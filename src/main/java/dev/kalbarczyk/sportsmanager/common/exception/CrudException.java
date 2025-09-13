package dev.kalbarczyk.sportsmanager.common.exception;

public sealed class CrudException extends RuntimeException
        permits CrudException.NotFound,
        CrudException.InvalidSortingArgument,
        CrudException.InvalidEntityIdException,
        CrudException.RelationRequirementsException,
        CrudException.NotImplementedEntityException {

    public CrudException(final String message) {
        super(message);
    }


    public static final class NotFound extends CrudException {
        public NotFound(final String message) {
            super(message);
        }
    }


    public static final class InvalidSortingArgument extends CrudException {
        public InvalidSortingArgument(final String message) {
            super(message);
        }
    }

    public static final class InvalidEntityIdException extends CrudException {
        public InvalidEntityIdException(final String message) {
            super(message);
        }
    }

    public static final class RelationRequirementsException extends CrudException {
        public RelationRequirementsException(final String message) {
            super(message);
        }
    }

    public static final class NotImplementedEntityException extends CrudException {
        public NotImplementedEntityException(final String message) {
            super(message);
        }
    }
}
