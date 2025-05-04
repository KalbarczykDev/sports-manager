package mas.domain.model.company;

import java.util.Objects;
import mas.domain.model.shared.ValueObject;

public final class NIP implements ValueObject<String> {
  private final String value;

  private NIP(String value) {
    this.value = value;
  }

  public static NIP of(String value) {
    validate(value);
    return new NIP(value);
  }

  private static void validate(String value) {
    if (value == null) {
      throw new IllegalArgumentException("NIP number is null");
    }
    if (value.isEmpty()) {
      throw new IllegalArgumentException("NIP number is empty");
    }
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
    return (this == o) || (o instanceof NIP other && value.equals(other.value));
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
