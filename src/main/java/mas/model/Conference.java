package mas.model;

import mas.model.abstraction.Event;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conference extends Event {

    private static final double BASIC_COMPENSATION = 1000.0;

    private final List<Fighter> fighters = new ArrayList<>();

    public Conference(String eventName, LocalDateTime date) {
        try {
            setEventName(eventName);
            setDate(date);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("Conference not created: " + e.getMessage());
        }
    }

    public void addFighter(Fighter fighter) {
        if(fighter == null) {
        throw new IllegalArgumentException("fighter is null");
        }

        if (!fighters.contains(fighter)) {
            fighters.add(fighter);
            fighter.addConference(this);
        }
    }

    public void removeFighter(Fighter fighter) {
        if (fighters.contains(fighter)) {
            fighters.remove(fighter);
            fighter.removeConference(this);
        }
    }

    public List<Fighter> getFighters() {
        return Collections.unmodifiableList(fighters);
    }

    @Override
    public double calculateTotalCompensationCost() {

        double total = 0.0;
        for (Fighter _ : fighters) {
            total += BASIC_COMPENSATION;
        }

        return total;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "date=" + getDate() +
                '}';
    }

    @Override
    public void removeFromExtent() {
        super.removeFromExtent();
    }
}
