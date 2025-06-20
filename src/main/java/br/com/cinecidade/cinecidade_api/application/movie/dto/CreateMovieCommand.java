package br.com.cinecidade.cinecidade_api.application.movie.dto;

public record CreateMovieCommand(
        String title,
        String synopsis,
        String rating,
        String genre,
        int durationMinutes,
        String trailerUrl,
        String status
) {}
