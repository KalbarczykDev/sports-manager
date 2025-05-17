package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mas.model.abstraction.Person;
import mas.model.attribute.Specialization;


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
        if (formerSelf == null) {
            throw new IllegalArgumentException("formerSelf is null");
        }

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
        if (fighter == null) {
            throw new IllegalArgumentException("fighter is null");
        }
        if (!clubFighters.contains(fighter)) {
            clubFighters.add(fighter);
            fighter.addCoach(this);
        }
    }

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
