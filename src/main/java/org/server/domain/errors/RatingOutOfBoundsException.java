package org.server.domain.errors;

public class RatingOutOfBoundsException extends ApiError {
    public RatingOutOfBoundsException(String message) {
        super(message);
    }
}
