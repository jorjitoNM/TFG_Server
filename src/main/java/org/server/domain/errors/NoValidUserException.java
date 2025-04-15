package org.server.domain.errors;

public class NoValidUserException extends RuntimeException {
    public NoValidUserException(String message) {
        super(message);
    }
}
