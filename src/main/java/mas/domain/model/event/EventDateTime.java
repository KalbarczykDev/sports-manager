package mas.domain.model.event;

import mas.domain.model.shared.ValueObject;

import java.time.LocalDateTime;

public class EventDateTime implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    private EventDateTime(LocalDateTime value) {
        this.value = value;
    }

    public static EventDateTime of(LocalDateTime value) {
        validate(value);
        return new EventDateTime(value);
    }


    private static void validate(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("Fight date and time cannot be null");
        }
        if (value.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Fight date and time cannot be in the past");
        }
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
        return this == o || (o instanceof EventDateTime other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
