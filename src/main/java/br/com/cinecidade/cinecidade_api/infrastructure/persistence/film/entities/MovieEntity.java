package br.com.cinecidade.cinecidade_api.infrastructure.persistence.film.entities;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.model.MovieStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String synopsis;
    private String rating;
    private String genre;
    private int durationMinutes;
    private String trailerUrl;
    private String status;
    private LocalDateTime createdAt;

    public Movie toDomain() {
        return Movie.from(id, title, synopsis, rating, genre, durationMinutes, trailerUrl, MovieStatus.fromString(status), createdAt);
    }

    public static MovieEntity fromDomain(Movie movie) {
        var entity = new MovieEntity();
        entity.id = movie.getId();
        entity.title = movie.getTitle();
        entity.genre = movie.getGenre();
        entity.status = movie.getStatus().name();
        return entity;
    }
}
