package mas.domain.model.company;

import mas.domain.model.shared.ValueObject;

import java.util.Objects;

public final class PhoneNumber implements ValueObject<String> {
    private final String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    public static PhoneNumber of(String value) {
        validate(value);
        return new PhoneNumber(value);
    }

    private static void validate(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Phone number is null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Phone number is empty");
        }
        if (!value.matches("\\+?[0-9]+")) {
            throw new IllegalArgumentException("Phone number is invalid");
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
        return (this == o) || (o instanceof PhoneNumber other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
