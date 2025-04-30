
package mas.domain.model.shared;

import mas.util.Util;

import java.util.Objects;

public final class Compensation implements ValueObject<Double> {

    private Double value;

    private Compensation(Double value) {
        this.value = value;
    }

    public static Compensation of(Double value) {
        validate(value);
        return new Compensation(value);
    }

    private static void validate(Double value) {
        Util.require(value != null, "Compensation is null");
        Util.require(value >= 0, "Compensation must be non-negative");
    }

    public void add(Compensation other) {
        Util.require(other != null, "Compensation to add is null");
        Util.require(other.value >= 0, "Compensation to add must be non-negative");
        this.value += other.value;
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
        if (!(o instanceof Compensation other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
