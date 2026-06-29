package org.henick.lottoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DrawNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDrawNotFound(DrawNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return ResponseEntity.status(response.status()).body(response);
    }
    @ExceptionHandler(UnknownGameException.class)
    public ResponseEntity<ErrorResponse> handleUnknownGame(UnknownGameException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return ResponseEntity.status(response.status()).body(response);
    }

}
