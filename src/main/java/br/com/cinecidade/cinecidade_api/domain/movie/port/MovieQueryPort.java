package br.com.cinecidade.cinecidade_api.domain.movie.port;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieQueryPort {
    Optional<Movie> findById(Long id);
    List<Movie> findAll();
}
