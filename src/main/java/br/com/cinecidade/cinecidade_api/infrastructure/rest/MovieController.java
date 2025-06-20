package br.com.cinecidade.cinecidade_api.infrastructure.rest;

import br.com.cinecidade.cinecidade_api.application.movie.PageResult;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieCommand;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieResponse;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.CreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.infrastructure.MetricCounter;
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

    public MovieController(CreateMovieUseCase createMovieUseCase) {
        this.createMovieUseCase = createMovieUseCase;
    }

    @MetricCounter(value = "movies.create", countSuccess = true, countError = true, recordTime = true)
    @PostMapping
    public ResponseEntity<CreateMovieResponse> create(@RequestBody CreateMovieRequest request) throws InterruptedException {
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


}