package mas.domain.model.event;

import java.time.LocalDateTime;
import mas.domain.model.shared.ValueObject;

public class EventDateTime implements ValueObject<LocalDateTime> {
  private final LocalDateTime value;

  private EventDateTime(LocalDateTime value) {
    this.value = value;
  }

  public static EventDateTime of(LocalDateTime value) {
    validate(value);
    return new EventDateTime(value);
  }

  private static void validate(LocalDateTime value) {
    // ogarniczenie atrybutu
    if (value == null) {
      throw new IllegalArgumentException("Event date and time cannot be null");
    }
    if (value.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Event date and time cannot be in the past");
    }

    LocalDateTime now = LocalDateTime.now();

    if (value.isAfter(now.plusYears(2))) {
      throw new IllegalArgumentException(
          "Event Date time cannot be more than 2 years in the future");
    }
  }

  @Override
  public LocalDateTime getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public boolean equals(Object o) {
    return this == o || (o instanceof EventDateTime other && value.equals(other.value));
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
