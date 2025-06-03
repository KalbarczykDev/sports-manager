package mas.exceptions;

public class ToManyFightsException extends RuntimeException {

  public ToManyFightsException() {
    super("Cannot plan more than 10 fights for a fighter.");
  }
}
