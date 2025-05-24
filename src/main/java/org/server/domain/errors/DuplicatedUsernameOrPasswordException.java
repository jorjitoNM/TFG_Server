package org.server.domain.errors;

public class DuplicatedUsernameOrPasswordException extends ApiError{
    public DuplicatedUsernameOrPasswordException(String message) {
        super(400, message);
    }
}
