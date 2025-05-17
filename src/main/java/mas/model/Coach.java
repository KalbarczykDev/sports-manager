package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mas.model.attribute.Specialization;
import mas.util.Util;

public class Coach extends Person {

  private Specialization specialization;

  private Person formerFighter;

  private final List<Fighter> individualFighters = new ArrayList<>();
  private final List<Fighter> clubFighters = new ArrayList<>();

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

  public Person getFormerFighter() {
    return formerFighter;
  }

  public void setFormerSelf(Person formerSelf) {
    Util.require(formerSelf != null, "formerFighter cannot be null");
    formerSelf.removeFromExtent();
    this.formerFighter = formerSelf;
  }

  public List<Fighter> getClubFighters() {
    return Collections.unmodifiableList(clubFighters);
  }

  public List<Fighter> getIndividualFighters() {
    return Collections.unmodifiableList(individualFighters);
  }

  public void addClubFighter(Fighter fighter) {
    Util.require(fighter != null, "Fighter cannot be null");
    if (!clubFighters.contains(fighter)) {
      clubFighters.add(fighter);
      fighter.addCoach(this);
    }
  }

  public void addIndividualFighter(Fighter fighter) {
    Util.require(fighter != null, "Fighter cannot be null");

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

  public void removeFighter(Fighter fighter) {
    if (clubFighters.contains(fighter)) {
      clubFighters.remove(fighter);
      fighter.removeCoach(this);
    }
    if (individualFighters.contains(fighter)) {
      individualFighters.remove(fighter);
    }
  }

  public Specialization getSpecialization() {
    return specialization;
  }

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
