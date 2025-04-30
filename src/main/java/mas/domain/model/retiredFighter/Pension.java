package mas.domain.model.retiredFighter;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.util.Objects;

public final class Pension implements ValueObject<Double> {

    private final Double value;

    private Pension(Double value) {
        this.value = value;
    }

    public static Pension of(Double value) {
        validate(value);
        return new Pension(value);
    }

    private static void validate(Double value) {
        Util.require(value != null, "Pension is null");
        Util.require(value > 0, "Pension must be greater than 0");
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " $";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pension other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
