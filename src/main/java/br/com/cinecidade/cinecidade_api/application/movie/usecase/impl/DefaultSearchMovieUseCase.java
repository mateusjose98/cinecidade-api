package br.com.cinecidade.cinecidade_api.application.movie.usecase.impl;

import br.com.cinecidade.cinecidade_api.application.movie.PageResult;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.SearchMovieUseCase;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;
import br.com.cinecidade.cinecidade_api.domain.shared.SearchQuery;
import br.com.cinecidade.cinecidade_api.infrastructure.rest.MovieResponseList;

import java.util.stream.Collectors;

public class DefaultSearchMovieUseCase implements SearchMovieUseCase {

    private final MovieCommandPort moviePort;

    public DefaultSearchMovieUseCase(MovieCommandPort moviePort) {
        this.moviePort = moviePort;
    }

    @Override
    public PageResult<MovieResponseList> execute(SearchQuery query) {
        var pageResult = moviePort.findAll(query);
        var items = pageResult.getContent().stream()
                .map(movie -> new MovieResponseList(
                        movie.getId().toString(),
                        movie.getTitle(),
                        movie.getGenre(),
                        movie.getStatus().name()
                )).collect(Collectors.toList());

        return new PageResult<>(
                items,
                pageResult.getPage(),
                pageResult.getPage(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }
}
