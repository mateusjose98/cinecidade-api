package br.com.cinecidade.cinecidade_api.infrastructure.rest;

import br.com.cinecidade.cinecidade_api.application.movie.PageResult;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieCommand;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieResponse;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.CreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.SearchMovieUseCase;
import br.com.cinecidade.cinecidade_api.domain.shared.SearchQuery;
import br.com.cinecidade.cinecidade_api.infrastructure.MetricCounter;
import br.com.cinecidade.cinecidade_api.infrastructure.persistence.MoviePresenter;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    private final CreateMovieUseCase createMovieUseCase;
    private final SearchMovieUseCase searchMovieUseCase;

    public MovieController(CreateMovieUseCase createMovieUseCase, SearchMovieUseCase searchMovieUseCase) {
        this.createMovieUseCase = createMovieUseCase;
        this.searchMovieUseCase = searchMovieUseCase;
    }

    @MetricCounter(value = "movies.create", countSuccess = true, countError = true, recordTime = true)
    @PostMapping
    public ResponseEntity<CreateMovieResponse> create(@RequestBody @Valid CreateMovieRequest request) throws InterruptedException {
        CreateMovieCommand command = new CreateMovieCommand(
                request.title(),
                request.synopsis(),
                request.rating(),
                request.genre(),
                request.durationMinutes(),
                request.trailerUrl(),
                request.status()
        );
        CreateMovieResponse response = createMovieUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResult<MovieResponseList>> listMovies(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        SearchQuery query = new SearchQuery(page, size, search, sort, direction);
        final var result = searchMovieUseCase.execute(query);
        return ResponseEntity.ok(result);
    }




}