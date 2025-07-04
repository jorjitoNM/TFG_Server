package org.server.domain.errors;

import lombok.Data;
import org.server.domain.common.DomainConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(NoteNotBelongUserException.class)
    public ResponseEntity<ApiError> handleNoteNotBelongException(NoteNotBelongUserException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoteNotAccessException.class)
    public ResponseEntity<ApiError> handleNoteNotAccessException(NoteNotAccessException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NoteNotFoundException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RatingOutOfBoundsException.class)
    public ResponseEntity<ApiError> handleOutOfBoundsException(RatingOutOfBoundsException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(InvalidNoteTypeException.class)
    public ResponseEntity<ApiError> handleInvalidNoteTypeException(InvalidNoteTypeException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoValidUserException.class)
    public ResponseEntity<ApiError> handleNoValidUserException(NoValidUserException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedUsernameOrPasswordException.class)
    public ResponseEntity<ApiError> handleNoValidUserException(DuplicatedUsernameOrPasswordException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(DomainConstants.AUTH_ERROR));
    }

    @ExceptionHandler(AutoFollowException.class)
    public ResponseEntity<ApiError> handleAutoFollowException(AutoFollowException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

}

@Data
class ApiErrorResponse {
    private int code;
    private String message;
    private LocalDateTime time;

    public ApiErrorResponse (int code,String message, LocalDateTime time) {
        this.code = code;
        this.message = message;
        this.time = time;
    }
}