import http from 'k6/http';
import { check, sleep } from 'k6';
import { uuidv4 } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

export const options = {
  scenarios: {
    mixed_requests: {
      executor: 'constant-arrival-rate',
      rate: 100, // 100 requests por segundo
      timeUnit: '1s',
      duration: '60s',
      preAllocatedVUs: 100,
      maxVUs: 200,
    },
  },
};

const BASE_URL = 'http://localhost:8080'; // ajuste para seu ambiente

// Gera um filme aleatório para POST
function generateMoviePayload() {
  return JSON.stringify({
    title: 'Movie ' + uuidv4().substring(0, 6),
    synopsis: 'Random generated movie for load test',
    rating: 'PG',
    genre: 'Action',
    durationMinutes: Math.floor(Math.random() * 180) + 60,
    trailerUrl: 'http://youtube.com/watch?v=' + uuidv4(),
    status: 'ACTIVE',
  });
}

export default function () {
  const isPost = Math.random() < 0.3; // 30% das vezes POST, 70% GET

  if (isPost) {
    const payload = generateMoviePayload();
    const res = http.post(`${BASE_URL}/movies`, payload, {
      headers: { 'Content-Type': 'application/json' },
    });

    check(res, {
      'POST status is 200': (r) => r.status === 200,
    });
  } else {
    const searchTerms = ['', 'action', 'comedy', 'drama'];
    const randomTerm = searchTerms[Math.floor(Math.random() * searchTerms.length)];

    const params = {
      search: randomTerm,
      page: Math.floor(Math.random() * 1000) + 1, // 🔁 aleatório de 1 a 1000
      size: 10,
      sort: 'title',
      direction: 'asc',
    };

    const query = Object.entries(params)
      .map(([k, v]) => `${k}=${encodeURIComponent(v)}`)
      .join('&');

    const res = http.get(`${BASE_URL}/movies?${query}`);

    check(res, {
      'GET status is 200': (r) => r.status === 200,
    });
  }

  sleep(1); // espera 1s entre execuções por VU (simulando usuário real)
}
