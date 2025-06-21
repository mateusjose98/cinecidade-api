package br.com.cinecidade.cinecidade_api.application.movie.usecase;

import br.com.cinecidade.cinecidade_api.application.movie.PageResult;
import br.com.cinecidade.cinecidade_api.domain.shared.SearchQuery;
import br.com.cinecidade.cinecidade_api.infrastructure.rest.MovieResponseList;

public interface SearchMovieUseCase {
    PageResult<MovieResponseList> execute(SearchQuery query);
}
