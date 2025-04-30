package mas.domain.model.event;

import mas.domain.model.shared.Compensation;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

import java.util.Objects;

public abstract class Event extends ObjectExtent {
    private EventName eventName;
    private EventDateTime date;

    public void setDate(EventDateTime date) {
        this.date = date;
    }

    public EventDateTime getDate() {
        return date;
    }

    public EventName getEventName() {
        return eventName;
    }

    public void setEventName(EventName eventName) {
        Util.require(eventName != null, "Event name cannot be null");
        this.eventName = eventName;
    }

    public abstract Compensation calculateTotalCompensationCost();


    @Override
    public String toString() {
        return "Event{" +
                "eventName=" + eventName +
                "date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}