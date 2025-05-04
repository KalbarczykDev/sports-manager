package mas.domain.model.event;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

public final class EventName implements ValueObject<String> {
  private final String value;

  private EventName(String value) {
    this.value = value;
  }

  public static EventName of(String value) {
    validate(value);
    return new EventName(value);
  }

  private static void validate(String value) {

    Util.require(value != null, " EventName is null");
    Util.require(!value.isEmpty(), " EventName is empty");
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EventName other)) return false;
    return value.equals(other.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
