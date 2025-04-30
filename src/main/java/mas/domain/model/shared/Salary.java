package mas.domain.model.shared;

import mas.util.Util;

import java.util.Objects;

public final class Salary implements ValueObject<Double> {

    private Double value;

    private Salary(Double value) {
        this.value = value;
    }

    public static Salary of(Double value) {
        validate(value);
        return new Salary(value);
    }

    private static void validate(Double value) {
        Util.require(value != null, "Salary is null");
        Util.require(value >= 0, "Salary must be non-negative");
    }

    public void add(Salary other) {
        Util.require(other != null, "Salary to add is null");
        Util.require(other.value >= 0, "Salary to add must be non-negative");
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
        if (!(o instanceof Salary other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
