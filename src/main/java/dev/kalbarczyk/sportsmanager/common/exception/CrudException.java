package dev.kalbarczyk.sportsmanager.common.exception;


/**
 * Base exception class for CRUD-related errors.
 * <p>
 * This is a sealed class permitting only specific CRUD exception types:
 * {@link NotFound}, {@link InvalidSortingArgument}, {@link RelationRequirementsException},
 * {@link NotImplementedEntityException}.
 */
public sealed class CrudException extends RuntimeException permits CrudException.NotFound, CrudException.InvalidSortingArgument, CrudException.RelationRequirementsException, CrudException.NotImplementedEntityException {

    /**
     * Constructs a new CRUD exception with the specified detail message.
     *
     * @param message the detail message
     */
    public CrudException(final String message) {
        super(message);
    }

    /**
     * Thrown when an entity is not found.
     */
    public static final class NotFound extends CrudException {
        public NotFound(final String message) {
            super(message);
        }
    }

    /**
     * Thrown when an invalid sorting argument is provided.
     */
    public static final class InvalidSortingArgument extends CrudException {
        public InvalidSortingArgument(final String message) {
            super(message);
        }
    }

    /**
     * Thrown when relation requirements are violated.
     */
    public static final class RelationRequirementsException extends CrudException {
        public RelationRequirementsException(final String message) {
            super(message);
        }
    }

    /**
     * Thrown when a CRUD operation is called on a non-implemented entity.
     */
    public static final class NotImplementedEntityException extends CrudException {
        public NotImplementedEntityException(final String message) {
            super(message);
        }
    }
}
