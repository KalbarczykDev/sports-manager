package dev.kalbarczyk.sportsmanager.person.exception;

import lombok.Getter;

import java.util.List;

public sealed class PersonException extends RuntimeException permits PersonException.Invalid {
    public PersonException(final String message) {
        super(message);
    }

    @Getter
    public static final class Invalid extends PersonException {
        private final List<String> messages;

        public Invalid(final List<String> messages) {
            super(messages.getFirst());
            this.messages = messages;
        }

    }

}
