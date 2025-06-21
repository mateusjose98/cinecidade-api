package br.com.cinecidade.cinecidade_api.infrastructure.beans;

import br.com.cinecidade.cinecidade_api.application.movie.usecase.CreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.SearchMovieUseCase;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.impl.DefaultCreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.impl.DefaultSearchMovieUseCase;
import br.com.cinecidade.cinecidade_api.domain.movie.port.MovieCommandPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfigBean {

    @Bean
    public CreateMovieUseCase createMovieUseCase(MovieCommandPort movieCommandPort) {
        return new DefaultCreateMovieUseCase(movieCommandPort);
    }

    @Bean
    public SearchMovieUseCase searchMovieUseCase(MovieCommandPort movieCommandPort) {
        return new DefaultSearchMovieUseCase(movieCommandPort);
    }

}
