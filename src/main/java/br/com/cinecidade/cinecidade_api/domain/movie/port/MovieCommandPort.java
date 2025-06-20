package br.com.cinecidade.cinecidade_api.domain.movie.port;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;

public interface MovieCommandPort {
    Movie save(Movie movie);
    void deleteById(Long id);
    Movie update(Movie movie);
}
