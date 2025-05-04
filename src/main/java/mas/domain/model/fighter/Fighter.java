package mas.domain.model.fighter;

import java.time.LocalDateTime;
import java.util.*;
import mas.domain.model.coach.Coach;
import mas.domain.model.coach.Specialization;
import mas.domain.model.conference.Conference;
import mas.domain.model.contract.Contract;
import mas.domain.model.fight.Fight;
import mas.domain.model.fightParticipation.FightParticipation;
import mas.domain.model.person.Name;
import mas.domain.model.person.Person;
import mas.domain.model.person.Surname;
import mas.domain.model.shared.Address;
import mas.domain.model.shared.Salary;
import mas.domain.model.sponsorship.Sponsorship;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

public class Fighter extends Person {

  private static Salary basicSalary = Salary.of(1000.0);

  private final Map<Specialization, Coach> coaches = new HashMap<>();
  private final List<Sponsorship> sponsorships = new ArrayList<>();
  private final List<Conference> conferences = new ArrayList<>();
  private final List<Contract> contracts = new ArrayList<>();
  private final List<FightParticipation> fightParticipations = new ArrayList<>();

  private List<Title> titles;
  private JoiningDate dateOfJoining;
  private Person formerSelf;

  public Fighter(Name name, Surname surname, Address address) {
    try {
      setName(name);
      setSurname(surname);
      setAddress(address);
      setDateOfJoining(JoiningDate.of(LocalDateTime.now()));
      setTitles(new ArrayList<>());
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Fighter not created:" + e.getMessage());
    }
  }

  public Fighter(Person person) {
    try {
      setFormerSelf(person);
      setName(person.getName());
      setSurname(person.getSurname());
      setAddress(person.getAddress());
      setDateOfJoining(JoiningDate.of(LocalDateTime.now()));
      setTitles(new ArrayList<>());
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Fighter not created:" + e.getMessage());
    }
  }

  public void addTitle(Title title) {
    titles.add(title);
  }

  public static Optional<Fighter> getCompetitorWithTheMostRewards() { // met klasowa
    Fighter returned = null;
    int topRewardsCount = 0;
    for (Fighter competitor : ObjectExtent.getExtent(Fighter.class)) {
      if (competitor.getTitles().size() > topRewardsCount) {
        returned = competitor;
      }
    }
    return Optional.ofNullable(returned);
  }

  // Getery/Setery

  public JoiningDate getDateOfJoining() {
    return dateOfJoining;
  }

  public void setDateOfJoining(JoiningDate dateOfJoining) {
    Util.require(dateOfJoining != null, "Date of joining cannot be null");
    this.dateOfJoining = dateOfJoining;
  }

  public static Salary getBasicSalary() {
    return basicSalary;
  }

  public static void setBasicSalary(Salary basicSalary) {
    Util.require(basicSalary != null, "Basic salary cannot be null");
    Fighter.basicSalary = basicSalary;
  }

  public Salary getSalary() { // atr pochodny

    double salary =
        (basicSalary.getValue() * (titles.size() + 1))
            + (basicSalary.getValue()
                * (LocalDateTime.now().getYear() - dateOfJoining.getValue().getYear()));
    return Salary.of(salary);
  }

  public void setTitles(List<Title> titles) {
    if (titles == null) {
      titles = new ArrayList<>();
    }
    this.titles = titles;
  }

  public List<Title> getTitles() {
    return Collections.unmodifiableList(titles);
  }

  public void addCoach(Coach coach) {
    Util.require(coach != null, "Coach cannot be null");

    Specialization specialization = coach.getSpecialization();

    // specjalizacja jest unikalna w ramach współpracy z zawodnikiem
    if (coaches.containsKey(specialization) && !coaches.get(specialization).equals(coach)) {
      throw new IllegalStateException("Fighter already has a coach with this specialization");
    }
    coaches.put(specialization, coach);
    coach.addClubFighter(this);
  }

  public void removeCoach(Coach coach) {
    if (coach == null) {
      return;
    }

    Specialization specialization = coach.getSpecialization();

    if (coaches.containsKey(specialization) && coaches.get(specialization).equals(coach)) {
      coaches.remove(specialization);
      coach.removeFighter(this);
    }
  }

  public void removeCoach(Specialization specialization) {
    if (coaches.containsKey(specialization)) {
      Coach coach = coaches.get(specialization);
      coaches.remove(specialization);
      coach.removeFighter(this);
    }
  }

  public Coach getCoachBySpecialization(Specialization specialization) {
    return coaches.getOrDefault(specialization, null);
  }

  public Map<Specialization, Coach> getCoaches() {
    return Collections.unmodifiableMap(coaches);
  }

  public List<Conference> getConferences() {
    return Collections.unmodifiableList(conferences);
  }

  public void addConference(Conference conference) {
    Util.require(conference != null, "Conference cannot be null");
    if (!conferences.contains(conference)) {
      conferences.add(conference);
      conference.addFighter(this);
    }
  }

  public void removeConference(Conference conference) {
    if (conferences.contains(conference)) {
      conferences.remove(conference);
      conference.removeFighter(this);
    }
  }

  public void addSponsorship(Sponsorship sponsorship) {
    Util.require(sponsorship != null, "Sponsor cannot be null");
    if (!sponsorships.contains(sponsorship)) {
      sponsorships.add(sponsorship);
      sponsorship.setFighter(this);
    }
  }

  public void removeSponsorship(Sponsorship sponsorship) {
    if (sponsorships.contains(sponsorship)) {
      sponsorships.remove(sponsorship);
      sponsorship.removeFromExtent();
    }
  }

  public void addFight(Fight fight) {
    new FightParticipation(this, fight);
  }

  public void addParticipation(FightParticipation fightParticipation) {
    Util.require(fightParticipation != null, "Fight participation cannot be null");
    validateYearlyFightLimit(
        fightParticipation.getFight().getGala().getDate().getValue().getYear());
    if (!fightParticipations.contains(fightParticipation)) {
      fightParticipations.add(fightParticipation);
    }
  }

  public void validateYearlyFightLimit(int fightYear) {

    long fightsThisYear =
        fightParticipations.stream()
            .map(fp -> fp.getFight().getGala().getDate().getValue().getYear())
            .filter(year -> year == fightYear)
            .count();

    if (fightsThisYear >= 10) {
      throw new IllegalStateException(
          "Fighter cannot participate in more than 10 fights in a year");
    }
  }

  public void removeParticipation(FightParticipation fightParticipation) {
    fightParticipations.remove(fightParticipation);
  }

  public List<FightParticipation> getFightParticipations() {
    return Collections.unmodifiableList(fightParticipations);
  }

  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  public void removeContract(Contract contract) {
    if (contracts.contains(contract)) {
      contracts.remove(contract);
      contract.removeFromExtent();
    }
  }

  public List<Contract> getContracts() {
    return Collections.unmodifiableList(contracts);
  }

  public List<Sponsorship> getSponsorships() {
    return Collections.unmodifiableList(sponsorships);
  }

  public Person getFormerSelf() {
    return formerSelf;
  }

  public void setFormerSelf(Person formerSelf) {
    Util.require(formerSelf != null, "formerSelf cannot be null");
    this.formerSelf.removeFromExtent();
    this.formerSelf = formerSelf;
  }

  @Override
  public String toString() { // przeciążenie
    return "Fighter{"
        + ", name='"
        + getName()
        + '\''
        + ", surname='"
        + getSurname()
        + '\''
        + ", address="
        + getAddress()
        + ", titles="
        + titles
        + ", contracts="
        + contracts.stream().map(Contract::getExpiresAt).toList()
        + ", coaches="
        + coaches.entrySet().stream()
            .map(
                entry ->
                    entry.getKey()
                        + ": "
                        + entry.getValue().getName()
                        + " "
                        + entry.getValue().getSurname())
            .toList()
        + ", sponsors="
        + sponsorships.stream().map(Sponsorship::getSponsor).toList()
        + ", dateOfJoining="
        + dateOfJoining
        + '}';
  }

  @Override
  public void removeFromExtent() {
    removeContracts();
    deleteParticipations();
    removeSponsorships();
    removeFromCoaches();
    super.removeFromExtent();
  }

  private void removeContracts() {
    for (Contract contract : contracts) {
      contract.removeFromExtent();
    }
  }

  private void deleteParticipations() {
    for (FightParticipation fightParticipation : fightParticipations) {
      fightParticipation.removeFromExtent();
    }
  }

  private void removeFromCoaches() {
    for (Coach coach : coaches.values()) {
      coach.removeFighter(this);
    }
    coaches.clear();
  }

  private void removeSponsorships() {
    for (Sponsorship sponsorship : sponsorships) {
      sponsorship.removeFromExtent();
    }
    sponsorships.clear();
  }
}
