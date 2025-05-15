package org.server.domain.errors;

public class NoteNotFoundException extends ApiError {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
