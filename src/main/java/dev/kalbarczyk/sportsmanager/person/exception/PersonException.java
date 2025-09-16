package dev.kalbarczyk.sportsmanager.person.exception;

import lombok.Getter;

import java.util.List;

/**
 * Base exception class for person-related errors.
 * Uses a sealed class hierarchy to restrict subclassing.
 */
public sealed class PersonException extends RuntimeException permits PersonException.Invalid {
    public PersonException(final String message) {
        super(message);
    }

    /**
     * Exception representing invalid person data.
     * Contains a list of validation error messages.
     */
    @Getter
    public static final class Invalid extends PersonException {
        private final List<String> messages;

        public Invalid(final List<String> messages) {
            super(messages.getFirst()); // Use the first message as the main exception message
            this.messages = messages;
        }

    }

}
