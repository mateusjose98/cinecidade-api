package br.com.cinecidade.cinecidade_api.domain.movie.model;

import br.com.cinecidade.cinecidade_api.domain.movie.validation.MovieValidator;
import br.com.cinecidade.cinecidade_api.domain.shared.Notification;
import br.com.cinecidade.cinecidade_api.domain.shared.ValidationException;


import java.time.LocalDateTime;

public class Movie {

    private Long id;
    private String title;
    private String synopsis;
    private String rating;
    private String genre;
    private int durationMinutes;
    private String trailerUrl;
    private MovieStatus status;
    private LocalDateTime createdAt;

    private Movie(Long id, String title, String synopsis, String rating, String genre, int durationMinutes,
                  String trailerUrl, MovieStatus status) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.rating = rating;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.trailerUrl = trailerUrl;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    private Movie() {
    }

    public static Movie from(
            Long id,
            String title,
            String synopsis,
            String rating,
            String genre,
            int durationMinutes,
            String trailerUrl,
            MovieStatus status,
            LocalDateTime createdAt
    ) {
        Movie movie = new Movie(id, title, synopsis, rating, genre, durationMinutes, trailerUrl, status);

        Notification notification = MovieValidator.validateUpdate(movie);
        if (notification.hasErrors()) {
            throw new ValidationException(notification.getErrors());
        }

        return movie;
    }


    public static Movie create(
            Long id,
            String title,
            String synopsis,
            String rating,
            String genre,
            int durationMinutes,
            String trailerUrl,
            MovieStatus status
    ) {
        Movie movie = new Movie(id, title, synopsis, rating, genre, durationMinutes, trailerUrl, status);
        Notification notification = MovieValidator.validate(movie);
        if (notification.hasErrors()) {
            throw new ValidationException(notification.getErrors());
        }

        return movie;
    }

    public void markAsInactive() {
        this.status = MovieStatus.INACTIVE;
    }

    public void changeStatus(MovieStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = newStatus;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
