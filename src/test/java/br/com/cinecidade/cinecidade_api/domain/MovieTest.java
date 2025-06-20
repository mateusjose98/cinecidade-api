package br.com.cinecidade.cinecidade_api.domain;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.model.MovieStatus;
import br.com.cinecidade.cinecidade_api.domain.shared.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void should_create_film_with_valid_data_using_update() {
        Movie movie = Movie.update(
                1L,
                "Interstellar",
                "A team of explorers travel through a wormhole in space.",
                "PG-13",
                "Sci-Fi",
                169,
                "https://youtube.com/trailer",
                MovieStatus.ACTIVE,
                LocalDateTime.now()
        );

        assertEquals("Interstellar", movie.getTitle());
        assertEquals(MovieStatus.ACTIVE, movie.getStatus());
    }

    @Test
    void should_throw_exception_when_updating_with_invalid_data() {
        var exception = assertThrows(ValidationException.class, () -> {
            Movie.update(
                    null, // ID ausente
                    "",   // title
                    "Short", // synopsis
                    "", // rating
                    "", // genre
                    -5, // duration
                    null,
                    null, // status
                    LocalDateTime.now()
            );
        });

        var errors = exception.getErrors();

        assertTrue(errors.contains("ID is required for update"));
        assertTrue(errors.contains("Title must not be empty"));
        assertTrue(errors.contains("Synopsis must be at least 15 characters long"));
        assertTrue(errors.contains("Duration must be greater than 0"));
        assertTrue(errors.contains("Status is required"));
    }

    @Test
    void should_mark_film_as_inactive() {
        Movie movie = Movie.create(
                null,
                "Inception",
                "A thief who steals corporate secrets through dreams.",
                "PG-13",
                "Thriller",
                148,
                "https://youtube.com/trailer",
                MovieStatus.ACTIVE
        );

        movie.markAsInactive();

        assertEquals(MovieStatus.INACTIVE, movie.getStatus());
    }

    @Test
    void should_change_status_successfully() {
        Movie movie = Movie.create(
                null,
                "Tenet",
                "An agent embarks on a mission to prevent World War III.",
                "PG-13",
                "Action",
                150,
                "https://youtube.com/trailer",
                MovieStatus.ACTIVE
        );

        movie.changeStatus(MovieStatus.INACTIVE);

        assertEquals(MovieStatus.INACTIVE, movie.getStatus());
    }

    @Test
    void should_throw_when_changing_status_to_null() {
        Movie movie = Movie.create(
                null,
                "Dunkirk",
                "Allied soldiers are surrounded by the German army.",
                "PG-13",
                "War",
                106,
                "https://youtube.com/trailer",
                MovieStatus.ACTIVE
        );

        assertThrows(IllegalArgumentException.class, () -> movie.changeStatus(null));
    }
}
