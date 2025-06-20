package br.com.cinecidade.cinecidade_api.domain.movie.validation;

import br.com.cinecidade.cinecidade_api.domain.movie.model.Movie;
import br.com.cinecidade.cinecidade_api.domain.shared.Notification;

public class MovieValidator {

    public static Notification validate(Movie movie) {
        Notification notification = new Notification();

        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            notification.addError("Title must not be empty");
        }

        if (movie.getSynopsis() == null || movie.getSynopsis().length() < 15) {
            notification.addError("Synopsis must be at least 15 characters long");
        }

        if (movie.getRating() == null || movie.getRating().isBlank()) {
            notification.addError("Rating is required");
        }

        if (movie.getGenre() == null || movie.getGenre().isBlank()) {
            notification.addError("Genre is required");
        }

        if (movie.getDurationMinutes() <= 0) {
            notification.addError("Duration must be greater than 0");
        }

        if (movie.getStatus() == null) {
            notification.addError("Status is required");
        }

        return notification;
    }

    public static Notification validateUpdate(Movie movie) {
        Notification notification = new Notification();

        if (movie.getId() == null) {
            notification.addError("ID is required for update");
        }

        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            notification.addError("Title must not be empty");
        }

        if (movie.getSynopsis() == null || movie.getSynopsis().length() < 15) {
            notification.addError("Synopsis must be at least 15 characters long");
        }

        if (movie.getDurationMinutes() <= 0) {
            notification.addError("Duration must be greater than 0");
        }

        if (movie.getStatus() == null) {
            notification.addError("Status is required");
        }

        return notification;
    }
}
