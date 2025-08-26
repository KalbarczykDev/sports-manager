package dev.kalbarczyk.sportsmanager.person.exception;

import dev.kalbarczyk.sportsmanager.common.model.dto.ApiError;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersonExceptionHandler {

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
