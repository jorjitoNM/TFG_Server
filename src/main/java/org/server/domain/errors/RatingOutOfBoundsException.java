package org.server.domain.errors;

public class RatingOutOfBoundsException extends RuntimeException {
    public RatingOutOfBoundsException(String message) {
        super(message);
    }
}
