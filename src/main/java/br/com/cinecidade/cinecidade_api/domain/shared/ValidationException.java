package br.com.cinecidade.cinecidade_api.domain.shared;

import java.util.Collections;
import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<String> errors;

    public ValidationException(List<String> errors) {
        super("Validation failed with " + errors.size() + " error(s).");
        this.errors = errors;
    }

    public ValidationException(String singleError) {
        this(List.of(singleError));
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String asMessage() {
        return String.join("; ", errors);
    }
}