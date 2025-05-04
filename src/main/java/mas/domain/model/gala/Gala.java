package mas.domain.model.gala;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import mas.domain.model.event.Event;
import mas.domain.model.event.EventDateTime;
import mas.domain.model.event.EventName;
import mas.domain.model.fight.Fight;
import mas.domain.model.fightParticipation.FightParticipation;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.shared.Compensation;
import mas.domain.model.shared.Salary;

public class Gala extends Event {
  private final Set<Fight> fights = new TreeSet<>(Comparator.comparingInt(Fight::getFightPriority));
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

  public void addFight(Fight fight, int priority) {
    if (!fights.contains(fight)) {
      fight.setFightPriority(priority);
      fight.setGala(this);
      fights.add(fight);
    }
  }

  public void removeFight(Fight fight) {
    if (fights.contains(fight)) {
      fights.remove(fight);
      fight.removeGala();
    }
  }

  public Set<Fight> getFights() {
    return Collections.unmodifiableSet(fights);
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

  public void printFightOrder() {
    int i = 1;
    for (Fight fight : fights) {
      System.out.println("Fight #" + (i++) + ": " + fight);
    }
  }

  @Override
  public String toString() {
    return "Gala{" + "date=}" + getDate() + '}';
  }
}
