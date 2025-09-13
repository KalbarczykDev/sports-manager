package dev.kalbarczyk.sportsmanager.common.exception;

import dev.kalbarczyk.sportsmanager.common.model.dto.ApiError;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CrudException.NotFound.class)
    public ResponseEntity<ApiError> handleNotFoundException(final CrudException.NotFound ex) {
        val apiError = ApiError.forGeneralError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    @ExceptionHandler(CrudException.InvalidSortingArgument.class)
    public ResponseEntity<ApiError> handleInvalidSortingArgumentException(final CrudException.InvalidSortingArgument ex) {
        val apiError = ApiError.forGeneralError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(CrudException.InvalidEntityIdException.class)
    public ResponseEntity<ApiError> handleInvalidEntityIdException(final CrudException.InvalidEntityIdException ex) {
        val apiError = ApiError.forGeneralError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(CrudException.RelationRequirementsException.class)
    public ResponseEntity<ApiError> handleRelationRequirementsException(final CrudException.RelationRequirementsException ex) {
        val apiError = ApiError.forGeneralError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(CrudException.NotImplementedEntityException.class)
    public ResponseEntity<ApiError> handleNotImplementedEntityException(final Exception ex) {
        val apiError = ApiError.forGeneralError(
                HttpStatus.NOT_IMPLEMENTED.value(),
                HttpStatus.NOT_IMPLEMENTED.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(apiError);
    }

}
