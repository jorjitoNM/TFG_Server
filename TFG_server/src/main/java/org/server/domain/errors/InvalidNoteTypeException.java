package org.server.domain.errors;

public class InvalidNoteTypeException extends RuntimeException{
    public InvalidNoteTypeException(String message) {
        super(message);
    }
}
