package br.com.cinecidade.cinecidade_api.domain.movie.port;

import br.com.cinecidade.cinecidade_api.application.movie.PageResult;
import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.shared.SearchQuery;

import java.util.List;

public interface MovieCommandPort {
    Movie save(Movie movie);
    void deleteById(Long id);
    Movie update(Movie movie);
    PageResult<Movie> findAll(SearchQuery query);
}
