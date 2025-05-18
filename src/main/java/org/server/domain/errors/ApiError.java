package org.server.domain.errors;


import java.time.LocalDate;

public class ApiError  {
    protected String message;
    public ApiError(String message) {
        this.message = message;
    }
}