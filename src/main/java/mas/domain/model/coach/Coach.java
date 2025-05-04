package mas.domain.model.coach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.person.Name;
import mas.domain.model.person.Person;
import mas.domain.model.person.Surname;
import mas.util.Util;

public class Coach extends Person {

  private Specialization specialization;

  private Person formerFighter;

  private final List<Fighter> fighters = new ArrayList<>(); // asocjacja z kwalifikowana

  public Coach(Name name, Surname surname, Specialization specialization) {
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

  public List<Fighter> getFighters() {
    return Collections.unmodifiableList(fighters);
  }

  public void addFighter(Fighter fighter) {
    Util.require(fighter != null, "Fighter cannot be null");
    if (!fighters.contains(fighter)) {
      fighters.add(fighter);
      fighter.addCoach(this);
    }
  }

  public void removeFighter(Fighter fighter) {
    if (fighters.contains(fighter)) {
      fighters.remove(fighter);
      fighter.removeCoach(this);
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
        + fighters.stream().map(f -> f.getName() + " " + f.getSurname()).toList()
        + '}';
  }

  @Override
  public void removeFromExtent() {
    removeFromFighters();
    super.removeFromExtent();
  }

  private void removeFromFighters() {
    for (Fighter fighter : fighters) {
      fighter.removeCoach(this);
    }
    fighters.clear();
  }
}
