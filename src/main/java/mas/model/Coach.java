package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.model.abstraction.Person;
import mas.model.attribute.Specialization;

/**
 * Represents a coach in the system, extending the Person class. A coach can have a specialization
 * and can train fighters, both individually and as part of a club.
 */
public class Coach extends Person {

  private Specialization specialization;

  private Person formerFighter;

  private final List<Fighter> individualFighters = new ArrayList<>();
  private final List<Fighter> clubFighters = new ArrayList<>();

  /**
   * Constructs a Coach with the specified name, surname, and specialization.
   *
   * @param name the name of the coach
   * @param surname the surname of the coach
   * @param specialization the specialization of the coach
   */
  public Coach(String name, String surname, Specialization specialization) {
    try {
      setName(name);
      setSurname(surname);
      setSpecialization(specialization);
    } catch (Exception e) {
      System.out.println("Coach not created: " + e.getMessage());
      removeFromExtent();
    }
  }

  /**
   * Constructs a Coach by using the former fighter's details and new specialization.
   *
   * @param formerSelf the former fighter who is now a coach
   * @param specialization the specialization of the coach
   */
  public Coach(Person formerSelf, Specialization specialization) {
    try {
      setName(formerSelf.getName());
      setSurname(formerSelf.getSurname());
      setSpecialization(specialization);
      setSpecialization(specialization);
      setFormerSelf(formerSelf);
    } catch (Exception e) {
      System.out.println("Coach not created: " + e.getMessage());
      removeFromExtent();
    }
  }

  /**
   * Returns the former fighter who is now a coach.
   *
   * @return the former fighter
   */
  public Person getFormerFighter() {
    return formerFighter;
  }

  /**
   * Sets the former fighter who is now a coach. This method removes the former fighter from the
   * extent.
   *
   * @param formerSelf the former fighter to set
   * @throws IllegalArgumentException if formerSelf is null
   */
  public void setFormerSelf(Person formerSelf) {
    if (formerSelf == null) {
      throw new IllegalArgumentException("formerSelf is null");
    }

    formerSelf.removeFromExtent();
    this.formerFighter = formerSelf;
  }

  /**
   * Returns an unmodifiable list of fighters that the coach trains as part of a club.
   *
   * @return the list of club fighters
   */
  public List<Fighter> getClubFighters() {
    return Collections.unmodifiableList(clubFighters);
  }

  /**
   * Returns an unmodifiable list of individual fighters that the coach trains individually.
   *
   * @return the list of individual fighters
   */
  public List<Fighter> getIndividualFighters() {
    return Collections.unmodifiableList(individualFighters);
  }

  /**
   * Adds a fighter to the club fighters list and assigns the coach to the fighter.
   *
   * @param fighter the fighter to add
   * @throws IllegalArgumentException if fighter is null
   */
  public void addClubFighter(Fighter fighter) {
    if (fighter == null) {
      throw new IllegalArgumentException("fighter is null");
    }
    if (!clubFighters.contains(fighter)) {
      clubFighters.add(fighter);
      fighter.addCoach(this);
    }
  }

  /**
   * Adds an individual fighter to the individual fighters list and assigns the coach to the
   * fighter.
   *
   * @param fighter the fighter to add
   * @throws IllegalArgumentException if fighter is null
   * @throws IllegalStateException if the fighter is not in the club or already an individual
   *     fighter
   */
  public void addIndividualFighter(Fighter fighter) {

    if (fighter == null) {
      throw new IllegalArgumentException("fighter is null");
    }

    if (!clubFighters.contains(fighter)) {
      throw new IllegalStateException("Fighter must be in club before becoming individual trainee");
    }

    if (individualFighters.contains(fighter)) {
      throw new IllegalStateException(
          "This figher is already an individual fighter assigned to coach");
    }

    individualFighters.add(fighter);
    fighter.addCoach(this);
  }

  /**
   * Removes a fighter from both the club fighters and individual fighters lists, and removes the
   * coach from the fighter's list of coaches.
   *
   * @param fighter the fighter to remove
   */
  public void removeFighter(Fighter fighter) {
    if (clubFighters.contains(fighter)) {
      clubFighters.remove(fighter);
      fighter.removeCoach(this);
    }
    if (individualFighters.contains(fighter)) {
      individualFighters.remove(fighter);
    }
  }

  /**
   * Returns the specialization of the coach.
   *
   * @return the specialization of the coach
   */
  public Specialization getSpecialization() {
    return specialization;
  }

  /**
   * Sets the specialization of the coach.
   *
   * @param specialization the specialization to set
   * @throws IllegalArgumentException if specialization is null
   */
  private void setSpecialization(Specialization specialization) {
    this.specialization = specialization;
  }

  @Override
  public String toString() {
    return "Coach{"
        + "name='"
        + getName()
        + '\''
        + ", surname='"
        + getSurname()
        + '\''
        + ", specialization="
        + specialization.getSpecialization()
        + ", fighters="
        + individualFighters.stream().map(f -> f.getName() + " " + f.getSurname()).toList()
        + '}';
  }

  @Override
  public void removeFromExtent() {
    removeFromFighters();
    super.removeFromExtent();
  }

  private void removeFromFighters() {
    for (Fighter fighter : clubFighters) {
      fighter.removeCoach(this);
    }
    clubFighters.clear();
    individualFighters.clear();
  }
}
