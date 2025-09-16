package dev.kalbarczyk.sportsmanager.common.model.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a standardized API error response.
 *
 * @param timestamp the time the error occurred, must not be null
 * @param status    HTTP status code
 * @param error     short error description, must not be null or blank
 * @param message   detailed error message, must not be null or blank
 * @param errors    optional list of validation or additional errors
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> errors
) {

    public ApiError {
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        Objects.requireNonNull(error, "error must not be null");
        Objects.requireNonNull(message, "message must not be null");

        if (error.isBlank()) {
            throw new IllegalArgumentException("error must not be blank.");
        }
        if (message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank.");
        }

        if (errors == null) {
            errors = Collections.emptyList();
        }
    }

    /**
     * Creates a general API error with a single message.
     *
     * @param status  HTTP status code
     * @param error   short error description
     * @param message detailed error message
     * @return a new ApiError instance
     */
    public static ApiError forGeneralError(final int status, final String error, final String message) {
        return new ApiError(LocalDateTime.now(), status, error, message, null);
    }

    /**
     * Creates an API error representing validation failures.
     *
     * @param status HTTP status code
     * @param error  short error description
     * @param errors list of validation error messages
     * @return a new ApiError instance
     */
    public static ApiError forValidationErrors(final int status, final String error, final List<String> errors) {
        String defaultMessage = "Validation Failed. Please check the 'errors' field for details.";
        return new ApiError(LocalDateTime.now(), status, error, defaultMessage, errors);
    }
}