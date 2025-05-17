package mas.model;

import java.time.LocalDateTime;
import java.util.*;

import mas.model.abstraction.Person;
import mas.model.association.FightParticipation;
import mas.model.attribute.Specialization;
import mas.model.attribute.Title;
import mas.model.attribute.Address;
import mas.model.association.Sponsorship;
import mas.model.data.ObjectExtent;


public class Fighter extends Person {

  private static double basicSalary = 1000.0;

  private final Map<Specialization, Coach> coaches = new HashMap<>();
  private final List<Sponsorship> sponsorships = new ArrayList<>();
  private final List<Conference> conferences = new ArrayList<>();
  private final List<Contract> contracts = new ArrayList<>();
  private final List<FightParticipation> fightParticipations = new ArrayList<>();

  private List<Title> titles;
  private LocalDateTime dateOfJoining;
  private Person formerSelf;

  public Fighter(String name, String surname, Address address) {
    try {
      setName(name);
      setSurname(surname);
      setAddress(address);
      setDateOfJoining(LocalDateTime.now());
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
      setDateOfJoining(LocalDateTime.now());
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

  public LocalDateTime getDateOfJoining() {
    return dateOfJoining;
  }

  public void setDateOfJoining(LocalDateTime dateOfJoining) {

    if(dateOfJoining == null) {
      throw new NullPointerException("dateOfJoining is null");
    }

    if(dateOfJoining.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("dateOfJoining is in the future");
    }

    this.dateOfJoining = dateOfJoining;
  }

  public static double getBasicSalary() {
    return basicSalary;
  }

  public static void setBasicSalary(double basicSalary) {
    if(basicSalary < 0){
      throw new IllegalArgumentException("Basic salary must be greater than zero");
    }

    Fighter.basicSalary = basicSalary;
  }

  public double getSalary() {

      return (basicSalary  * (titles.size() + 1))
          + (basicSalary
              * (LocalDateTime.now().getYear() - dateOfJoining.getYear()));
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


    if(coach == null){
      throw new NullPointerException("Coach cannot be null");
    }


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

    if(conference == null){
      throw new NullPointerException("Conference cannot be null");
    }

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


    if(sponsorship == null){
      throw new NullPointerException("Sponsorship cannot be null");
    }

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

    if(fightParticipation == null){
      throw new NullPointerException("Fight participation cannot be null");
    }

    validateYearlyFightLimit(
        fightParticipation.getFight().getGala().getDate().getYear());
    if (!fightParticipations.contains(fightParticipation)) {
      fightParticipations.add(fightParticipation);
    }
  }

  public void validateYearlyFightLimit(int fightYear) {

    long fightsThisYear =
        fightParticipations.stream()
            .map(fp -> fp.getFight().getGala().getDate().getYear())
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
    if(formerSelf == null){
      throw new NullPointerException("formerSelf cannot be null");
    }
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
