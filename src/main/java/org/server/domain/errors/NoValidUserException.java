package org.server.domain.errors;

public class NoValidUserException extends ApiError {
    public NoValidUserException(String message) {
        super(message);
    }
}
