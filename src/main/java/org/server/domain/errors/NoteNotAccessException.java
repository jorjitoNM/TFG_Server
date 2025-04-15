package org.server.domain.errors;

public class NoteNotAccessException extends RuntimeException{
    public NoteNotAccessException(String message) {
        super(message);
    }
}
