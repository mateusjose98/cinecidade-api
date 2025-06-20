package br.com.cinecidade.cinecidade_api.domain.movie.model;

public enum MovieStatus {
    ACTIVE, INACTIVE, COMING_SOON, REMOVED;

    public static MovieStatus fromString(String value) {
        try {
            return MovieStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
           return null;
        }
    }
}
