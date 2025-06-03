package mas.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mas.exceptions.ToManyFightsException;
import mas.model.abstraction.Person;
import mas.model.association.FightParticipation;
import mas.model.association.Sponsorship;
import mas.model.attribute.Address;
import mas.model.attribute.Specialization;
import mas.model.attribute.Title;
import mas.model.data.ObjectExtent;

/**
 * Represents a fighter in the system, extending the Person class. A fighter can have multiple
 * coaches, sponsorships, conferences, contracts, and fight participations. The fighter's salary is
 * calculated based on their titles and years of experience.
 */
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

  /**
   * Constructs a Fighter with the specified name, surname, and address. The date of joining is set
   * to the current time, and an empty list of titles is initialized.
   *
   * @param name the name of the fighter
   * @param surname the surname of the fighter
   * @param address the address of the fighter
   */
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

  /**
   * Constructs a Fighter using the details of a Person object. The date of joining is set to the
   * current time, and an empty list of titles is initialized.
   *
   * @param formerSelf the Person object containing the formerSelf fighter's details
   */
  public Fighter(Person formerSelf) {
    try {
      setFormerSelf(formerSelf);
      setName(formerSelf.getName());
      setSurname(formerSelf.getSurname());
      setAddress(formerSelf.getAddress());
      setDateOfJoining(LocalDateTime.now());
      setTitles(new ArrayList<>());
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Fighter not created:" + e.getMessage());
    }
  }

  /**
   * Adds a title to the fighter's list of titles. The title must not be null.
   *
   * @param title the title to add
   */
  public void addTitle(Title title) {
    titles.add(title);
  }

  /**
   * Removes a title from the fighter's list of titles. If the title is null, it does nothing.
   *
   * @param title the title to remove
   */
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

  /**
   * Returns the date of joining for the fighter.
   *
   * @return the date of joining
   */
  public LocalDateTime getDateOfJoining() {
    return dateOfJoining;
  }

  /**
   * Sets the date of joining for the fighter. The date must not be null and cannot be in the
   * future.
   *
   * @param dateOfJoining the date of joining to set
   * @throws NullPointerException if dateOfJoining is null
   * @throws IllegalArgumentException if dateOfJoining is in the future
   */
  public void setDateOfJoining(LocalDateTime dateOfJoining) {

    if (dateOfJoining == null) {
      throw new NullPointerException("dateOfJoining is null");
    }

    if (dateOfJoining.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("dateOfJoining is in the future");
    }

    this.dateOfJoining = dateOfJoining;
  }

  /**
   * Returns the basic salary for fighters.
   *
   * @return the basic salary
   */
  public static double getBasicSalary() {
    return basicSalary;
  }

  /**
   * Sets the basic salary for fighters. The salary must be greater than zero.
   *
   * @param basicSalary the basic salary to set
   * @throws IllegalArgumentException if basicSalary is less than or equal to zero
   */
  public static void setBasicSalary(double basicSalary) {
    if (basicSalary < 0) {
      throw new IllegalArgumentException("Basic salary must be greater than zero");
    }

    Fighter.basicSalary = basicSalary;
  }

  /**
   * Calculates the salary of the fighter based on their titles and years of experience. The salary
   * is calculated as follows:
   *
   * <ul>
   *   <li>Basic salary multiplied by the number of titles plus one
   *   <li>Plus basic salary multiplied by the number of years since joining
   * </ul>
   *
   * @return the calculated salary
   */
  public double getSalary() {

    return (basicSalary * (titles.size() + 1))
        + (basicSalary * (LocalDateTime.now().getYear() - dateOfJoining.getYear()));
  }

  /**
   * Sets the titles for the fighter. If the provided list is null, it initializes an empty list.
   *
   * @param titles the list of titles to set
   */
  public void setTitles(List<Title> titles) {
    if (titles == null) {
      titles = new ArrayList<>();
    }
    this.titles = titles;
  }

  /**
   * Returns an unmodifiable list of titles held by the fighter.
   *
   * @return an unmodifiable list of titles
   */
  public List<Title> getTitles() {
    return Collections.unmodifiableList(titles);
  }

  /**
   * Adds a coach to the fighter's list of coaches. The coach must not be null, and the
   * specialization of the coach must be unique within the fighter's coaches.
   *
   * @param coach the coach to add
   * @throws NullPointerException if coach is null
   * @throws IllegalStateException if a coach with the same specialization already exists
   */
  public void addCoach(Coach coach) {

    if (coach == null) {
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

  /**
   * Removes a coach from the fighter's list of coaches. If the coach is null, it does nothing. If
   * the coach is not found in the fighter's coaches, it does nothing.
   *
   * @param coach the coach to remove
   */
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

  /**
   * Removes a coach from the fighter's list of coaches based on their specialization. If the
   * specialization is not found, it does nothing.
   *
   * @param specialization the specialization of the coach to remove
   */
  public void removeCoach(Specialization specialization) {
    if (coaches.containsKey(specialization)) {
      Coach coach = coaches.get(specialization);
      coaches.remove(specialization);
      coach.removeFighter(this);
    }
  }

  /**
   * Returns the coach associated with the specified specialization. If no coach is found for the
   * specialization, it returns null.
   *
   * @param specialization the specialization of the coach to retrieve
   * @return the coach associated with the specialization, or null if not found
   */
  public Coach getCoachBySpecialization(Specialization specialization) {
    return coaches.getOrDefault(specialization, null);
  }

  /**
   * Returns an unmodifiable map of coaches associated with the fighter, where the key is the
   * specialization and the value is the coach.
   *
   * @return an unmodifiable map of coaches
   */
  public Map<Specialization, Coach> getCoaches() {
    return Collections.unmodifiableMap(coaches);
  }

  /**
   * Returns an unmodifiable list of conferences associated with the fighter.
   *
   * @return an unmodifiable list of conferences
   */
  public List<Conference> getConferences() {
    return Collections.unmodifiableList(conferences);
  }

  /**
   * Adds a conference to the fighter's list of conferences. The conference must not be null, and it
   * must not already be in the list of conferences.
   *
   * @param conference the conference to add
   * @throws NullPointerException if conference is null
   */
  public void addConference(Conference conference) {

    if (conference == null) {
      throw new NullPointerException("Conference cannot be null");
    }

    if (!conferences.contains(conference)) {
      conferences.add(conference);
      conference.addFighter(this);
    }
  }

  /**
   * Removes a conference from the fighter's list of conferences. If the conference is null, it does
   * nothing. If the conference is not found in the fighter's conferences, it does nothing.
   *
   * @param conference the conference to remove
   */
  public void removeConference(Conference conference) {
    if (conferences.contains(conference)) {
      conferences.remove(conference);
      conference.removeFighter(this);
    }
  }

  /**
   * Adds a sponsorship to the fighter. The sponsorship must not be null, and it must not already be
   * in the list of sponsorships.
   *
   * @param sponsorship the sponsorship to add
   * @throws NullPointerException if sponsorship is null
   */
  public void addSponsorship(Sponsorship sponsorship) {

    if (sponsorship == null) {
      throw new NullPointerException("Sponsorship cannot be null");
    }

    if (!sponsorships.contains(sponsorship)) {
      sponsorships.add(sponsorship);
      sponsorship.setFighter(this);
    }
  }

  /**
   * Removes a sponsorship from the fighter's list of sponsorships. If the sponsorship is null, it
   * does nothing. If the sponsorship is not found in the fighter's sponsorships, it does nothing.
   *
   * @param sponsorship the sponsorship to remove
   */
  public void removeSponsorship(Sponsorship sponsorship) {
    if (sponsorships.contains(sponsorship)) {
      sponsorships.remove(sponsorship);
      sponsorship.removeFromExtent();
    }
  }

  /**
   * Adds a fight participation to the fighter. The fight participation is created automatically
   * when adding a fight.
   *
   * @param fight the fight to add
   */
  public void addFight(Fight fight) {
    new FightParticipation(this, fight);
  }

  /**
   * Adds a fight participation to the fighter. The fight participation must not be null, and it
   * should not exceed the yearly fight limit of 10 fights.
   *
   * @param fightParticipation the fight participation to add
   * @throws NullPointerException if fightParticipation is null
   * @throws ToManyFightsException if the fighter has already participated in 10 fights this year
   */
  public void addParticipation(FightParticipation fightParticipation) {

    if (fightParticipation == null) {
      throw new NullPointerException("Fight participation cannot be null");
    }

    validateYearlyFightLimit();
    if (!fightParticipations.contains(fightParticipation)) {
      fightParticipations.add(fightParticipation);
    }
  }

  /**
   * Validates the yearly fight limit for the fighter. If the fighter has already participated in 10
   * fights this year, it throws a ToManyFightsException.
   *
   * @throws ToManyFightsException if the fighter has already participated in 10 fights this year
   */
  public void validateYearlyFightLimit() {
    if (fightParticipations.size() >= 10) {
      throw new ToManyFightsException();
    }
  }

  /**
   * Removes a fight participation from the fighter. If the fight participation is null, it does
   * nothing. If the fight participation is not found in the fighter's fight participations, it does
   * nothing.
   *
   * @param fightParticipation the fight participation to remove
   */
  public void removeParticipation(FightParticipation fightParticipation) {
    fightParticipations.remove(fightParticipation);
  }

  /**
   * Returns an unmodifiable list of fight participations associated with the fighter.
   *
   * @return an unmodifiable list of fight participations
   */
  public List<FightParticipation> getFightParticipations() {
    return Collections.unmodifiableList(fightParticipations);
  }

  /**
   * Adds a contract to the fighter's list of contracts. The contract must not be null, and it must
   * not already be in the list of contracts.
   *
   * @param contract the contract to add
   * @throws NullPointerException if contract is null
   */
  public void addContract(Contract contract) {
    contracts.add(contract);
  }

  /**
   * Removes a contract from the fighter's list of contracts. If the contract is null, it does
   * nothing. If the contract is not found in the fighter's contracts, it does nothing.
   *
   * @param contract the contract to remove
   */
  public void removeContract(Contract contract) {
    if (contracts.contains(contract)) {
      contracts.remove(contract);
      contract.removeFromExtent();
    }
  }

  /**
   * Returns an unmodifiable list of contracts associated with the fighter.
   *
   * @return an unmodifiable list of contracts
   */
  public List<Contract> getContracts() {
    return Collections.unmodifiableList(contracts);
  }

  /**
   * Returns an unmodifiable list of sponsorships associated with the fighter.
   *
   * @return an unmodifiable list of sponsorships
   */
  public List<Sponsorship> getSponsorships() {
    return Collections.unmodifiableList(sponsorships);
  }

  /**
   * Returns the former self of the fighter. This is used to keep track of the fighter's previous
   * state before they were converted from a Person to a Fighter.
   *
   * @return the former self of the fighter
   */
  public Person getFormerSelf() {
    return formerSelf;
  }

  /**
   * Sets the former self of the fighter. This method removes the former self from the extent to
   * ensure that it is no longer tracked as a Person.
   *
   * @param formerSelf the former self to set
   * @throws NullPointerException if formerSelf is null
   */
  public void setFormerSelf(Person formerSelf) {
    if (formerSelf == null) {
      throw new NullPointerException("formerSelf cannot be null");
    }
    this.formerSelf.removeFromExtent();
    this.formerSelf = formerSelf;
  }

  @Override
  public String toString() { // przeciążenie
    // return "Fighter{"
    //     + ", name='"
    //     + getName()
    //     + '\''
    //     + ", surname='"
    //     + getSurname()
    //     + '\''
    //     + ", address="
    //     + getAddress()
    //     + ", titles="
    //     + titles
    //     + ", contracts="
    //     + contracts.stream().map(Contract::getExpiresAt).toList()
    //     + ", coaches="
    //     + coaches.entrySet().stream()
    //         .map(
    //             entry ->
    //                 entry.getKey()
    //                     + ": "
    //                     + entry.getValue().getName()
    //                     + " "
    //                     + entry.getValue().getSurname())
    //         .toList()
    //     + ", sponsors="
    //     + sponsorships.stream().map(Sponsorship::getSponsor).toList()
    //     + ", dateOfJoining="
    //     + dateOfJoining
    //     + '}';
    return this.getName()
        + " "
        + this.getSurname()
        + " ("
        + this.fightParticipations.size()
        + " planed fights)";
  }

  @Override
  public void removeFromExtent() {
    removeContracts();
    deleteParticipations();
    removeSponsorships();
    removeFromCoaches();
    super.removeFromExtent();
  }

  /**
   * Removes all contracts associated with this fighter. This method is called when the fighter is
   * removed from the extent to ensure that all associated contracts are also removed.
   */
  private void removeContracts() {
    for (Contract contract : contracts) {
      contract.removeFromExtent();
    }
  }

  /**
   * Deletes all fight participations associated with the fighter. This method is called when the
   * fighter is removed from the extent to ensure that all associated fight participations are also
   * removed.
   */
  private void deleteParticipations() {
    for (FightParticipation fightParticipation : fightParticipations) {
      fightParticipation.removeFromExtent();
    }
  }

  /**
   * Removes all coaches associated with this fighter. This method is called when the fighter is
   * removed from the extent to ensure that all associated coaches are also removed.
   */
  private void removeFromCoaches() {
    for (Coach coach : coaches.values()) {
      coach.removeFighter(this);
    }
    coaches.clear();
  }

  /**
   * Removes all sponsorships associated with this fighter. This method is called when the fighter
   * is removed from the extent to ensure that all associated sponsorships are also removed.
   */
  private void removeSponsorships() {
    for (Sponsorship sponsorship : sponsorships) {
      sponsorship.removeFromExtent();
    }
    sponsorships.clear();
  }
}
