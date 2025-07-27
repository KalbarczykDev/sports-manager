package dev.kalbarczyk.sportsmanager.common.exception;

public sealed class CrudException extends RuntimeException permits CrudException.NotFound {

    public CrudException(String message) {
        super(message);
    }


    public static final class NotFound extends CrudException {
        public NotFound(String message) {
            super(message);
        }
    }
}
