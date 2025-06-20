package br.com.cinecidade.cinecidade_api.infrastructure.persistence.exceptions;

public class MovieEntityNotFoundException extends RuntimeException{
    public MovieEntityNotFoundException(String message) {
        super(message);
    }
}
