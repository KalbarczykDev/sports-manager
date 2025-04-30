package mas.domain.model.person;


import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.util.Objects;

public final class Surname implements ValueObject<String> {
    private final String value;

    private Surname(String value) {
        this.value = value;
    }

    public static Surname of(String value) {
        validate(value);
        return new Surname(value);
    }

    private static void validate(String value) {
        Util.require(value != null, "Surname is null");
        Util.require(!value.isEmpty(), "Surname is empty");
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
        if (!(o instanceof Surname other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
