package br.com.cinecidade.cinecidade_api.application.movie.usecase.impl;

import br.com.cinecidade.cinecidade_api.application.movie.usecase.GetOneMovieUseCase;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;
import br.com.cinecidade.cinecidade_api.infrastructure.rest.MovieResponseList;

public class DefaultGetOneMovieUseCase implements GetOneMovieUseCase {

    private final MovieCommandPort moviePort;

    public DefaultGetOneMovieUseCase(MovieCommandPort moviePort) {
        this.moviePort = moviePort;
    }


    @Override
    public MovieResponseList execute(Integer id) {
        final var movie = moviePort.getOne(id);
        return new MovieResponseList(
                movie.getId().toString(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getStatus().name()
        );
    }
}
