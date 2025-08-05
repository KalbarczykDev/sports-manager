package dev.kalbarczyk.sportsmanager.common.exception;

import dev.kalbarczyk.sportsmanager.common.model.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CrudException.NotFound.class)
    public ResponseEntity<ApiError> handleNotFoundException(CrudException.NotFound ex) {
        ApiError apiError = ApiError.forGeneralError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }
}
