package dev.kalbarczyk.sportsmanager.common.exception;

public sealed class CrudException extends RuntimeException permits CrudException.NotFound {

    public CrudException(final String message) {
        super(message);
    }


    public static final class NotFound extends CrudException {
        public NotFound(final String message) {
            super(message);
        }
    }
}
