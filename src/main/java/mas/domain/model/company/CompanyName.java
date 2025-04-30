package mas.domain.model.company;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.util.Objects;

public final class CompanyName implements ValueObject<String> {
    private final String value;

    private CompanyName(String value) {
        this.value = value;
    }

    public static CompanyName of(String value) {
        validate(value);
        return new CompanyName(value);
    }

    private static void validate(String value) {
        Util.require(value != null, "Company name is null");
        Util.require(!value.isEmpty(), "Company name is empty");
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
        return (this == o) || (o instanceof CompanyName other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
