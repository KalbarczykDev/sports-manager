package dev.kalbarczyk.sportsmanager.common.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        @NonNull LocalDateTime timestamp,
        int status,
        @NotBlank String error,
        @NotBlank String message,
        List<String> errors
) {

    public ApiError {
        if (errors == null) {
            errors = List.of();
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