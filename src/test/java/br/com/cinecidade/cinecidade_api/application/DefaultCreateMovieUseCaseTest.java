package br.com.cinecidade.cinecidade_api.application;

import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieCommand;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieResponse;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.impl.DefaultCreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.model.MovieStatus;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;
import br.com.cinecidade.cinecidade_api.domain.shared.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultCreateMovieUseCaseTest {

    private MovieCommandPort movieCommandPort;
    private DefaultCreateMovieUseCase useCase;

    @BeforeEach
    void setUp() {
        movieCommandPort = mock(MovieCommandPort.class);
        useCase = new DefaultCreateMovieUseCase(movieCommandPort);
    }

    @Test
    void should_create_film_successfully() {
        // Arrange
        CreateMovieCommand command = new CreateMovieCommand(
                "Inception",
                "A mind-bending thriller about dreams within dreams.",
                "PG-13",
                "Sci-Fi",
                148,
                "https://youtube.com/trailer",
                "ACTIVE"
        );

        Movie fakeSavedMovie = Movie.create(
                1L,
                command.title(),
                command.synopsis(),
                command.rating(),
                command.genre(),
                command.durationMinutes(),
                command.trailerUrl(),
                MovieStatus.ACTIVE
        );

        when(movieCommandPort.save(any(Movie.class))).thenReturn(fakeSavedMovie);

        CreateMovieResponse response = useCase.execute(command);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Inception", response.title());
        assertNotNull(response.createdAt());

        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
        verify(movieCommandPort).save(captor.capture());
        Movie capturedMovie = captor.getValue();
        assertEquals("Inception", capturedMovie.getTitle());
        assertEquals("PG-13", capturedMovie.getRating());
        assertEquals(MovieStatus.ACTIVE, capturedMovie.getStatus());
    }

    @Test
    void should_throw_validation_exception_when_command_is_invalid() {
        // Arrange: comando com título vazio, duração inválida etc.
        CreateMovieCommand command = new CreateMovieCommand(
                "", // título vazio
                "short", // sinopse muito curta
                "", // rating ausente
                "", // genre ausente
                0,  // duração inválida
                null,
                "" // status ausente
        );

        // Act & Assert
        ValidationException ex = assertThrows(ValidationException.class, () -> {
            useCase.execute(command);
        });

        assertTrue(ex.getErrors().contains("Title must not be empty"));
        assertTrue(ex.getErrors().contains("Synopsis must be at least 15 characters long"));
        assertTrue(ex.getErrors().contains("Rating is required"));
        assertTrue(ex.getErrors().contains("Genre is required"));
        assertTrue(ex.getErrors().contains("Duration must be greater than 0"));
        assertTrue(ex.getErrors().contains("Status is required"));

        verifyNoInteractions(movieCommandPort);
    }

}
