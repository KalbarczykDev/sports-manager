package mas.domain.model.company;

import mas.domain.model.shared.ValueObject;

import java.util.regex.Pattern;

public final class Email implements ValueObject<String> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        validate(value);
        return new Email(value);
    }

    private static void validate(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Email is null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Email is empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
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
        return (this == o) || (o instanceof Email other && value.equalsIgnoreCase(other.value));
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
