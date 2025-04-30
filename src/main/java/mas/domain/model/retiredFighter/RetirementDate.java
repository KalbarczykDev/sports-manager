package mas.domain.model.retiredFighter;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.time.LocalDateTime;
import java.util.Objects;

public class RetirementDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    private RetirementDate(LocalDateTime value) {
        this.value = value;
    }

    public static RetirementDate of(LocalDateTime value) {
        validate(value);
        return new RetirementDate(value);
    }

    private static void validate(LocalDateTime value) {
        Util.require(value != null, "Retirement date cannot be null");
        Util.require(value.isBefore(LocalDateTime.now()), "Expiration date cannot be in the future");
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
        return this == o || (o instanceof RetirementDate other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
