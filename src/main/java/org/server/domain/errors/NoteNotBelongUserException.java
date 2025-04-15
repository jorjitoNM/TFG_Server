package org.server.domain.errors;

public class NoteNotBelongUserException extends RuntimeException {
    public NoteNotBelongUserException(String message) {
        super(message);
    }
}