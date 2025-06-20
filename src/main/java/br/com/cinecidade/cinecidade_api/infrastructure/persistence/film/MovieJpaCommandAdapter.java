package br.com.cinecidade.cinecidade_api.infrastructure.persistence.film;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;
import br.com.cinecidade.cinecidade_api.infrastructure.mapper.MovieEntityMapper;
import br.com.cinecidade.cinecidade_api.infrastructure.persistence.exceptions.MovieEntityNotFoundException;
import br.com.cinecidade.cinecidade_api.infrastructure.persistence.film.entities.MovieEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieJpaCommandAdapter implements MovieCommandPort {

    private final MovieJpaRepository repository;
    private final MovieEntityMapper mapper;

    public MovieJpaCommandAdapter(MovieJpaRepository repository, MovieEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Movie save(Movie movie) {
        MovieEntity entity = mapper.toEntity(movie);
        MovieEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new MovieEntityNotFoundException("Film with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

    @Override
    public Movie update(Movie movie) {
        if (movie.getId() == null || !repository.existsById(movie.getId())) {
            throw new MovieEntityNotFoundException("Cannot update non-existent film with ID " + movie.getId());
        }
        MovieEntity updated = repository.save(mapper.toEntity(movie));
        return mapper.toDomain(updated);
    }
}
