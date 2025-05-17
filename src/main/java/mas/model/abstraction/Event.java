package mas.model.abstraction;

import mas.model.data.ObjectExtent;


import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Event extends ObjectExtent {
    private String eventName;
    private LocalDateTime date;

    public void setDate(LocalDateTime date) {
        if (date == null) {
      throw new IllegalArgumentException("Event date and time cannot be null");
    }
    if (date.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Event date and time cannot be in the past");
    }

    LocalDateTime now = LocalDateTime.now();

    if (date.isAfter(now.plusYears(2))) {
      throw new IllegalArgumentException(
          "Event Date time cannot be more than 2 years in the future");
    }
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        if(eventName == null) {
            throw new IllegalArgumentException("Event name cannot be null");
        }
        if(eventName.isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        this.eventName = eventName;
    }

    public abstract double calculateTotalCompensationCost();


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