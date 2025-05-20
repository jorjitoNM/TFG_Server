package org.server.domain.errors;


import java.time.LocalDate;

public class ApiError extends RuntimeException {
    protected int code;
    protected String message;
    protected LocalDate time;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
        this.time = LocalDate.now();
    }

    public ApiError(String message) {
        this.code = 500;
        this.message = message;
        this.time = LocalDate.now();
    }
}