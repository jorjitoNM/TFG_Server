package org.server.domain.errors;

public class UserNotFound extends ApiError {
    public UserNotFound(String message) {
        super(message);
    }
}
