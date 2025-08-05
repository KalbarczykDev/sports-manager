package dev.kalbarczyk.sportsmanager.competitor.exception;

import dev.kalbarczyk.sportsmanager.common.model.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CompetitorExceptionHandler {

    @ExceptionHandler(CompetitorException.Invalid.class)
    public ResponseEntity<ApiError> handleInvalidCompetitorException(CompetitorException.Invalid ex) {
        ApiError apiError = ApiError.forValidationErrors(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessages()
        );
        return ResponseEntity.badRequest().body(apiError);
    }
}
