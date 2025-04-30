package mas.domain.model.contract;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.time.LocalDateTime;
import java.util.Objects;

public final class SigningDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    private SigningDate(LocalDateTime value) {
        this.value = value;
    }

    public static SigningDate of(LocalDateTime value) {
        validate(value);
        return new SigningDate(value);
    }

    private static void validate(LocalDateTime value) {
        Util.require(value != null, "Signed date cannot be null");
        Util.require(value.isBefore(LocalDateTime.now()), "Signed date cannot be in the future");
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
        return this == o || (o instanceof SigningDate other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
