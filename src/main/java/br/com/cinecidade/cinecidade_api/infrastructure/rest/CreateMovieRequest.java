package br.com.cinecidade.cinecidade_api.infrastructure.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMovieRequest(
        @NotBlank String title,
        @Size(min = 15) String synopsis,
        @NotBlank String rating,
        @NotBlank String genre,
        @Min(1) int durationMinutes,
        String trailerUrl,
        @NotBlank String status
) {}