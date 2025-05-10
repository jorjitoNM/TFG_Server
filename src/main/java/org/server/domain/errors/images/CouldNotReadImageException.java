package org.server.domain.errors.images;

import org.server.domain.errors.ApiError;

public class CouldNotReadImageException extends ApiError {
    public CouldNotReadImageException(int code, String message) {
        super(code, message);
    }
}
