package br.com.cinecidade.cinecidade_api.application.movie.usecase;

import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieCommand;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieResponse;

public interface CreateMovieUseCase {
    CreateMovieResponse execute(CreateMovieCommand command);
}
