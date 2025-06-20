package br.com.cinecidade.cinecidade_api.application.movie.dto;

import java.time.LocalDateTime;

public record CreateMovieResponse(
        Long id,
        String title,
        LocalDateTime createdAt
) {}
