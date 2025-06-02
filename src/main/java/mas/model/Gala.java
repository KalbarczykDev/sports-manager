package mas.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import mas.model.abstraction.Event;
import mas.model.association.FightParticipation;

public class Gala extends Event {
  private final Set<Fight> fights = new TreeSet<>(Comparator.comparingInt(Fight::getFightPriority));
  private static final Double COMPENSATION_RATE = 0.1;

  public Gala(String eventName, LocalDateTime date) {
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
  public double calculateTotalCompensationCost() {
    double total = 0.0;

    for (Fight fight : fights) {
      for (FightParticipation fightParticipation : fight.getParticipants()) {
        Fighter fighter = fightParticipation.getFighter();
        double salary = fighter.getSalary();

        double compensation = salary * COMPENSATION_RATE;
        total += compensation;
      }
    }
    return total;
  }

  @Override
  public String toString() {
    return "Gala{" + "date=" + (getDate() != null ? getDate() : "N/A") + '}';
  }
}
