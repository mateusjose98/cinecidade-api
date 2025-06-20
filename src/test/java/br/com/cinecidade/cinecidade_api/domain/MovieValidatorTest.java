package br.com.cinecidade.cinecidade_api.domain;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.model.MovieStatus;
import br.com.cinecidade.cinecidade_api.domain.movie.validation.MovieValidator;
import br.com.cinecidade.cinecidade_api.domain.shared.Notification;
import br.com.cinecidade.cinecidade_api.domain.shared.ValidationException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class MovieValidatorTest {

    @Test
    void should_return_no_errors_when_film_is_valid() {
        Movie movie = Movie.create(
                null,
                "Inception",
                "A mind-bending thriller about dreams within dreams.",
                "PG-13",
                "Sci-Fi",
                148,
                "https://youtube.com/trailer",
                MovieStatus.ACTIVE
        );

        Notification notification = MovieValidator.validate(movie);

        assertFalse(notification.hasErrors());
        assertNotNull(movie.getRating());
        assertNotNull(movie.getCreatedAt());

    }

    @Test
    void should_throw_exception_when_fields_are_invalid() {
        var exception = assertThrows(ValidationException.class, () -> {
            Movie.create(
                    null,
                    "", // title
                    "Too short", // synopsis
                    "", // rating
                    "", // genre
                    -10, // duration
                    null,
                    null
            );
        });

        var errors = exception.getErrors();

        assertEquals(6, errors.size());
        assertTrue(errors.stream().anyMatch(e -> e.contains("Title must not be empty")));
        assertTrue(errors.stream().anyMatch(e -> e.contains("Synopsis must be at least 15 characters long")));
        assertTrue(errors.stream().anyMatch(e -> e.contains("Rating is required")));
        assertTrue(errors.stream().anyMatch(e -> e.contains("Genre is required")));
        assertTrue(errors.stream().anyMatch(e -> e.contains("Duration must be greater than 0")));
        assertTrue(errors.stream().anyMatch(e -> e.contains("Status is required")));
    }
}
