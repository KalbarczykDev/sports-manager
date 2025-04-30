package mas.domain.model.fighter;

import mas.domain.model.shared.ValueObject;
import mas.util.Util;

import java.time.LocalDateTime;
import java.util.Objects;

public final class JoiningDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    private JoiningDate(LocalDateTime value) {
        this.value = value;
    }

    public static JoiningDate of(LocalDateTime value) {
        validate(value);
        return new JoiningDate(value);
    }

    private static void validate(LocalDateTime value) {
        Util.require(value != null, "Joined Date cannot be null");
        Util.require(value.isBefore(LocalDateTime.now()), "Joined Date cannot be in the future");
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof JoiningDate other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
