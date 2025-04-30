package mas.domain.model.fightParticipation;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.util.Objects;

public final class Points implements ValueObject<Integer> {
    private final int value;

    private Points(int value) {
        this.value = value;
    }

    public static Points of(int value) {
        validate(value);
        return new Points(value);
    }

    public static void validate(int value) {
        Util.require(value >= 0, "Points cannot be negative");
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Points p && value == p.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
