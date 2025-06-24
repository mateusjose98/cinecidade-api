package br.com.cinecidade.cinecidade_api.infrastructure.persistence.film;

import br.com.cinecidade.cinecidade_api.application.movie.PageResult;
import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;
import br.com.cinecidade.cinecidade_api.domain.shared.SearchQuery;
import br.com.cinecidade.cinecidade_api.infrastructure.mapper.MovieEntityMapper;
import br.com.cinecidade.cinecidade_api.infrastructure.persistence.exceptions.MovieEntityNotFoundException;
import br.com.cinecidade.cinecidade_api.infrastructure.persistence.film.entities.MovieEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public PageResult<Movie> findAll(SearchQuery query) {
        var pageRequest = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        var pageResult = repository.findByTitleContainingIgnoreCase(query.terms(), pageRequest);

        var movies = pageResult.getContent()
                .stream()
                .map(MovieEntity::toDomain)
                .collect(Collectors.toList());

        return new PageResult<>(
                movies,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public Movie getOne(Integer id) {
        return repository.findById(Long.valueOf(id)).map(MovieEntity::toDomain).orElseThrow();
    }
}
