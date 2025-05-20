package org.server.domain.errors.images;


public class CouldNotReadImageException extends RuntimeException {
    public CouldNotReadImageException(String message) {
        super( message);
    }
}
