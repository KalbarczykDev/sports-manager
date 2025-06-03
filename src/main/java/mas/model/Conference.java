package mas.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.model.abstraction.Event;

/**
 * Represents a conference event in the system, extending the Event class. A conference can have
 * multiple fighters associated with it, and it calculates the total compensation cost based on the
 * number of fighters.
 */
public class Conference extends Event {

  private static final double BASIC_COMPENSATION = 1000.0;

  private final List<Fighter> fighters = new ArrayList<>();

  /**
   * Constructs a Conference with the specified event name and date.
   *
   * @param eventName the name of the conference
   * @param date the date and time of the conference
   */
  public Conference(String eventName, LocalDateTime date) {
    try {
      setEventName(eventName);
      setDate(date);
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Conference not created: " + e.getMessage());
    }
  }

  /**
   * Constructs a Conference with the specified date.
   *
   * @param date the date and time of the conference
   */
  public void addFighter(Fighter fighter) {
    if (fighter == null) {
      throw new IllegalArgumentException("fighter is null");
    }

    if (!fighters.contains(fighter)) {
      fighters.add(fighter);
      fighter.addConference(this);
    }
  }

  /**
   * Removes a fighter from the conference.
   *
   * @param fighter the fighter to remove
   */
  public void removeFighter(Fighter fighter) {
    if (fighters.contains(fighter)) {
      fighters.remove(fighter);
      fighter.removeConference(this);
    }
  }

  /**
   * Returns an unmodifiable list of fighters associated with the conference.
   *
   * @return an unmodifiable list of fighters
   */
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
    return "Conference{" + "date=" + getDate() + '}';
  }

  @Override
  public void removeFromExtent() {
    super.removeFromExtent();
  }
}
