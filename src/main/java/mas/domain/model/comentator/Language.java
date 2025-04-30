package mas.domain.model.comentator;

import mas.domain.model.shared.ValueObject;

public class Language implements ValueObject<String> {
    private final String value;

    private Language(String value) {
        this.value = value;
    }

    public static Language of(String value) {
        validate(value);
        return new Language(value);
    }

    private static void validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Language cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
