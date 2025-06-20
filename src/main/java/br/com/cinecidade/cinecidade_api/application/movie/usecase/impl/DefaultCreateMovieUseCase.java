package br.com.cinecidade.cinecidade_api.application.movie.usecase.impl;

import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieCommand;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieResponse;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.CreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.movie.model.MovieStatus;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;

public class DefaultCreateMovieUseCase implements CreateMovieUseCase {

    private final MovieCommandPort movieCommandPort;

    public DefaultCreateMovieUseCase(MovieCommandPort movieCommandPort) {
        this.movieCommandPort = movieCommandPort;
    }

    @Override
    public CreateMovieResponse execute(CreateMovieCommand command) {
        Movie movie = Movie.create(
                null,
                command.title(),
                command.synopsis(),
                command.rating(),
                command.genre(),
                command.durationMinutes(),
                command.trailerUrl(),
                MovieStatus.fromString(command.status().toUpperCase())
        );
        Movie saved = movieCommandPort.save(movie);
        return new CreateMovieResponse(saved.getId(), saved.getTitle(), saved.getCreatedAt());
    }
}
