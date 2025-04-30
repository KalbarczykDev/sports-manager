package mas.domain.model.person;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

public final class Name implements ValueObject<String> {
    private final String value;

    private Name(String value) {
        this.value = value;
    }

    public static Name of(String value) {
        validate(value);
        return new Name(value);
    }

    private static void validate(String value) {
        Util.require(value != null, "Name is null");
        Util.require(!value.isEmpty(), "Name is empty");
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
        if (!(o instanceof Name other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
