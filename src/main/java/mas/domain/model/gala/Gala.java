package mas.domain.model.gala;

import mas.domain.model.event.Event;
import mas.domain.model.event.EventDateTime;
import mas.domain.model.event.EventName;
import mas.domain.model.fight.Fight;
import mas.domain.model.fightParticipation.FightParticipation;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.shared.Compensation;
import mas.domain.model.shared.Salary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gala extends Event {

    private final List<Fight> fights = new ArrayList<>();

    private static final Double COMPENSATION_RATE = 0.1;

    public Gala(EventName eventName, EventDateTime date) {
        try {
            setEventName(eventName);
            setDate(date);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("Gala not created: " + e.getMessage());
        }
    }

    public void addFight(Fight fight) {
        if (!fights.contains(fight)) {
            fights.add(fight);
            fight.setGala(this);
        }
    }

    public void removeFight(Fight fight) {
        if (fights.contains(fight)) {
            fights.remove(fight);
            fight.removeGala();
        }
    }

    public List<Fight> getFights() {
        return Collections.unmodifiableList(fights);
    }

    @Override
    public Compensation calculateTotalCompensationCost() {
        Compensation total = Compensation.of(0.0);

        for (Fight fight : fights) {
            for (FightParticipation fightParticipation : fight.getParticipants()) {
                Fighter fighter = fightParticipation.getFighter();
                Salary salary = fighter.getSalary();


                Compensation compensation = Compensation.of(salary.getValue() * COMPENSATION_RATE);
                total.add(compensation);
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Gala{" +
                "date=}" + getDate()
                + '}';
    }
}
