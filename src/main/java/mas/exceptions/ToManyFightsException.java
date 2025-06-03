package mas.exceptions;

/**
 * Exception thrown when a fighter is planned to have more than 10 fights. This is a runtime
 * exception, indicating that the error is due to business logic rather than a programming error.
 */
public class ToManyFightsException extends RuntimeException {

  /** Constructs a new ToManyFightsException with a default error message. */
  public ToManyFightsException() {
    super("Cannot plan more than 10 fights for a fighter.");
  }
}
