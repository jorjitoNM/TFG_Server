package org.server.domain.errors;

public class NoteNotAccessException extends ApiError{
    public NoteNotAccessException(String message) {
        super(message);
    }
}
