package br.com.cinecidade.cinecidade_api.infrastructure.mapper;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.model.MovieStatus;
import br.com.cinecidade.cinecidade_api.infrastructure.persistence.film.entities.MovieEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieEntityMapper {

    public MovieEntity toEntity(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieEntity movieEntity = new MovieEntity();

        movieEntity.setId( movie.getId() );
        movieEntity.setTitle( movie.getTitle() );
        movieEntity.setSynopsis( movie.getSynopsis() );
        movieEntity.setRating( movie.getRating() );
        movieEntity.setGenre( movie.getGenre() );
        movieEntity.setDurationMinutes( movie.getDurationMinutes() );
        movieEntity.setTrailerUrl( movie.getTrailerUrl() );
        if ( movie.getStatus() != null ) {
            movieEntity.setStatus( movie.getStatus().name() );
        }
        movieEntity.setCreatedAt( movie.getCreatedAt() );

        return movieEntity;
    }

    public Movie toDomain(MovieEntity entity) {
        if ( entity == null ) {
            return null;
        }
       return Movie.create(
                entity.getId(),
                entity.getTitle(),
                entity.getSynopsis(),
                entity.getRating(),
                entity.getGenre(),
                entity.getDurationMinutes(),
                entity.getTrailerUrl(),
                MovieStatus.fromString(entity.getStatus().toUpperCase())
        );
    }
}