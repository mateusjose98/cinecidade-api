package br.com.cinecidade.cinecidade_api.application.movie.usecase;

import br.com.cinecidade.cinecidade_api.infrastructure.rest.MovieResponseList;

public interface GetOneMovieUseCase {
    MovieResponseList execute(Integer id);
}
