package org.server.domain.errors;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiError extends RuntimeException {
    private int code;
    private String message;
    private LocalDateTime time;

    public ApiError (String message) {
        this.code = 500;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}