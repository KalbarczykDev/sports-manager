package dev.kalbarczyk.sportsmanager.common.model.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public static ApiError forGeneralError(final int status, final String error, final String message) {
        return new ApiError(LocalDateTime.now(), status, error, message, null);
    }

    public static ApiError forValidationErrors(final int status, final String error, final List<String> errors) {
        String defaultMessage = "Validation Failed. Please check the 'errors' field for details.";
        return new ApiError(LocalDateTime.now(), status, error, defaultMessage, errors);
    }
}