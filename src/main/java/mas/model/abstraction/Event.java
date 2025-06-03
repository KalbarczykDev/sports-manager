package mas.model.abstraction;

import java.time.LocalDateTime;
import java.util.Objects;
import mas.model.data.ObjectExtent;

/**
 * Abstract class representing an event in the system. It contains common attributes and methods for
 * all types of events.
 */
public abstract class Event extends ObjectExtent {
  private String eventName;
  private LocalDateTime date;

  /**
   * Sets the date and time of the event. The date must not be null, must not be in the past, and
   * must not be more than 2 years in the future.
   *
   * @param date the date and time to set for the event
   * @throws IllegalArgumentException if the date is null, in the past, or more than 2 years in the
   *     future
   */
  public void setDate(LocalDateTime date) {
    if (date == null) {
      throw new IllegalArgumentException("Event date and time cannot be null");
    }
    if (date.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Event date and time cannot be in the past");
    }

    LocalDateTime now = LocalDateTime.now();

    if (date.isAfter(now.plusYears(2))) {
      throw new IllegalArgumentException(
          "Event Date time cannot be more than 2 years in the future");
    }
    this.date = date;
  }

  /**
   * Gets the date and time of the event.
   *
   * @return the date and time of the event
   */
  public LocalDateTime getDate() {
    return date;
  }

  /**
   * Gets the name of the event.
   *
   * @return the name of the event
   */
  public String getEventName() {
    return eventName;
  }

  /**
   * Sets the name of the event. The name must not be null or empty.
   *
   * @param eventName the name to set
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setEventName(String eventName) {
    if (eventName == null) {
      throw new IllegalArgumentException("Event name cannot be null");
    }
    if (eventName.isEmpty()) {
      throw new IllegalArgumentException("Event name cannot be empty");
    }
    this.eventName = eventName;
  }

  public abstract double calculateTotalCompensationCost();

  @Override
  public String toString() {
    return "Event{" + "eventName=" + eventName + "date=" + date + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Objects.equals(date, event.date);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(date);
  }
}

