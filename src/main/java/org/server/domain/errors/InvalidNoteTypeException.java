package org.server.domain.errors;

public class InvalidNoteTypeException extends ApiError{
    public InvalidNoteTypeException(String message) {
        super(message);
    }
}
