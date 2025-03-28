package org.server.domain.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}