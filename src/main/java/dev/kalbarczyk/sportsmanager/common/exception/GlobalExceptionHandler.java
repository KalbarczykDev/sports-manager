package dev.kalbarczyk.sportsmanager.common.exception;

import dev.kalbarczyk.sportsmanager.common.model.dto.ApiError;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles application-wide exceptions and maps them to standardized API error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link CrudException.NotFound} exceptions.
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 404(Not Found)
     */
    @ExceptionHandler(CrudException.NotFound.class)
    public ResponseEntity<ApiError> handleNotFoundException(final CrudException.NotFound ex) {
        val apiError = ApiError.forGeneralError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    /**
     * Handles {@link CrudException.InvalidSortingArgument} exceptions.
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400(Bad Request)
     */
    @ExceptionHandler(CrudException.InvalidSortingArgument.class)
    public ResponseEntity<ApiError> handleInvalidSortingArgumentException(final CrudException.InvalidSortingArgument ex) {
        val apiError = ApiError.forGeneralError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


    /**
     * Handles {@link CrudException.RelationRequirementsException} exceptions.
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 400(Bad Request)
     */
    @ExceptionHandler(CrudException.RelationRequirementsException.class)
    public ResponseEntity<ApiError> handleRelationRequirementsException(final CrudException.RelationRequirementsException ex) {
        val apiError = ApiError.forGeneralError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    /**
     * Handles {@link CrudException.NotImplementedEntityException} exceptions.
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} containing an {@link ApiError} with status 501(Not Implemented)
     */
    @ExceptionHandler(CrudException.NotImplementedEntityException.class)
    public ResponseEntity<ApiError> handleNotImplementedEntityException(final Exception ex) {
        val apiError = ApiError.forGeneralError(HttpStatus.NOT_IMPLEMENTED.value(), HttpStatus.NOT_IMPLEMENTED.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(apiError);
    }

}
