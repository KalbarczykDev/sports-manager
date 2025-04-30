package mas.domain.model.contract;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ExpirationDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    private ExpirationDate(LocalDateTime value) {
        this.value = value;
    }

    public static ExpirationDate of(LocalDateTime value) {
        validate(value);
        return new ExpirationDate(value);
    }

    private static void validate(LocalDateTime value) {
        Util.require(value != null, "Expiration date cannot be null");
        Util.require(value.isAfter(LocalDateTime.now()), "Expiration date cannot be in the past");
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
        return this == o || (o instanceof ExpirationDate other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
