package org.server.domain.errors;

public class DuplicatedUsernameOrPasswordException extends RuntimeException{
    public DuplicatedUsernameOrPasswordException(String message) {
        super(message);
    }
}
