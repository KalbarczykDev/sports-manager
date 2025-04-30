package mas.domain.model.contractor;

import mas.domain.model.shared.ValueObject;

public class ServiceDescription implements ValueObject<String> {
    private final String value;

    private ServiceDescription(String value) {
        this.value = value;
    }

    public static ServiceDescription of(String value) {
        validate(value);
        return new ServiceDescription(value);
    }

    private static void validate(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Service description is null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Service description is empty");
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
        return (this == o) || (o instanceof ServiceDescription other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }



}
