package dev.kalbarczyk.sportsmanager.person.exception;

import dev.kalbarczyk.sportsmanager.common.model.dto.ApiError;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for person-related exceptions.
 */
@ControllerAdvice
public class PersonExceptionHandler {

    /**
     * Handles invalid person data exceptions and returns validation error details.
     *
     * @param ex the thrown {@link PersonException.Invalid}
     * @return {@link ResponseEntity} containing {@link ApiError} with validation messages
     */
    @ExceptionHandler(PersonException.Invalid.class)
    public ResponseEntity<ApiError> handleInvalidCompetitorException(final PersonException.Invalid ex) {
        val apiError = ApiError.forValidationErrors(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessages()
        );
        return ResponseEntity.badRequest().body(apiError);
    }
}
