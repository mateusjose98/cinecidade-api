package br.com.cinecidade.cinecidade_api.infrastructure.persistence;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.infrastructure.rest.MovieResponseList;

public class MoviePresenter {
    public static MovieResponseList present(Movie movie) {
        return new MovieResponseList(
                movie.getId().toString(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getStatus().name()
        );
    }
}
