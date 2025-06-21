package br.com.cinecidade.cinecidade_api.infrastructure;

import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieCommand;
import br.com.cinecidade.cinecidade_api.application.movie.dto.CreateMovieResponse;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.CreateMovieUseCase;
import br.com.cinecidade.cinecidade_api.application.movie.usecase.SearchMovieUseCase;
import br.com.cinecidade.cinecidade_api.infrastructure.rest.CreateMovieRequest;
import br.com.cinecidade.cinecidade_api.infrastructure.rest.MovieController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateMovieUseCase createMovieUseCase;

    @MockitoBean
    private SearchMovieUseCase searchMovieUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateFilmSuccessfully() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest(
                "Inception",
                "A mind-bending thriller about dreams within dreams.",
                "PG-13",
                "Sci-Fi",
                148,
                "https://youtube.com/inception",
                "ACTIVE"
        );

        CreateMovieResponse response = new CreateMovieResponse(1L, "Inception", LocalDateTime.now());

        Mockito.when(createMovieUseCase.execute(Mockito.any(CreateMovieCommand.class)))
                .thenReturn(response);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void shouldReturn400WhenTitleIsBlank() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest(
                "",  // título inválido
                "This synopsis is long enough.",
                "PG-13",
                "Action",
                120,
                null,
                "ACTIVE"
        );

        mockMvc.perform(post("/movies")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

}
