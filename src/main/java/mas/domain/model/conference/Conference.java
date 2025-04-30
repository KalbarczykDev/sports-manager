package mas.domain.model.conference;

import mas.domain.model.event.Event;
import mas.domain.model.event.EventDateTime;
import mas.domain.model.event.EventName;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.shared.Compensation;
import mas.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conference extends Event {

    private static final Compensation BASIC_COMPENSATION = Compensation.of(1000.5);

    private final List<Fighter> fighters = new ArrayList<>();

    public Conference(EventName eventName, EventDateTime date) {
        try {
            setEventName(eventName);
            setDate(date);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("Conference not created: " + e.getMessage());
        }
    }

    public void addFighter(Fighter fighter) {
        Util.require(fighter != null, "Fighter cannot be null");
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
    public Compensation calculateTotalCompensationCost() {

        Compensation total = Compensation.of(0.0);
        for (Fighter _ : fighters) {
            total.add(BASIC_COMPENSATION);
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
