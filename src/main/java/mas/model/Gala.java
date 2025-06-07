package mas.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import mas.model.abstraction.Event;
import mas.model.association.FightParticipation;

/**
 * Represents a gala event in the system, extending the Event class. A gala can have multiple fights
 * associated with it, and it calculates the total compensation cost based on the fighters involved
 * in those fights.
 */
public class Gala extends Event {
  public static class FightComparator implements Comparator<Fight>, Serializable {
    @Override
    public int compare(Fight f1, Fight f2) {
      return Integer.compare(f1.getFightPriority(), f2.getFightPriority());
    }
  }

  private final Set<Fight> fights = new TreeSet<>(new FightComparator());

  private static final Double COMPENSATION_RATE = 0.1;

  /**
   * Constructs a Gala with the specified event name and date.
   *
   * @param eventName the name of the gala
   * @param date the date and time of the gala
   */
  public Gala(String eventName, LocalDateTime date) {
    try {
      setEventName(eventName);
      setDate(date);
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Gala not created: " + e.getMessage());
    }
  }

  /**
   * Constructs a Gala with the specified date.
   *
   * @param date the date and time of the gala
   */
  public void addFight(Fight fight, int priority) {
    if (!fights.contains(fight)) {
      fight.setFightPriority(priority);
      fight.setGala(this);
      fights.add(fight);
    }
  }

  /**
   * Removes a fight from the gala.
   *
   * @param fight the fight to remove
   */
  public void removeFight(Fight fight) {
    if (fights.contains(fight)) {
      fights.remove(fight);
      fight.removeGala();
    }
  }

  /**
   * Returns an unmodifiable set of fights associated with the gala.
   *
   * @return an unmodifiable set of fights
   */
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return getEventName() + " – " + (getDate() != null ? getDate().format(formatter) : "N/A");
  }
}
