package dev.kalbarczyk.sportsmanager.competitor.exception;

import lombok.Getter;

import java.util.List;

public sealed class CompetitorException extends RuntimeException permits CompetitorException.Invalid {
    public CompetitorException(final String message) {
        super(message);
    }

    @Getter
    public static final class Invalid extends CompetitorException {
        private final List<String> messages;

        public Invalid(final List<String> messages) {
            super(messages.getFirst());
            this.messages = messages;
        }

    }

}
