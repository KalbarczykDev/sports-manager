package mas.model;

import java.time.LocalDateTime;
import mas.model.abstraction.Person;

/**
 * Represents a retired fighter in the system, extending the Person class. A retired fighter has a
 * former self (the fighter before retirement), a pension, and a retirement date.
 */
public class RetiredFighter extends Person {
  private Person formerSelf;
  private double pension;
  private LocalDateTime retirementDate;

  /**
   * Constructs a RetiredFighter with the specified former self and pension.
   *
   * @param formerSelf the person who was a fighter before retirement
   * @param pension the pension amount for the retired fighter
   */
  public RetiredFighter(Person formerSelf, double pension) {
    try {
      setName(formerSelf.getName());
      setSurname(formerSelf.getSurname());
      setAddress(formerSelf.getAddress());
      setRetirementDate(LocalDateTime.now());
      setPension(pension);
      setFormerSelf(formerSelf);
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("RetiredFighter not created: " + e.getMessage());
    }
  }

  /**
   * Returns the pension amount for the retired fighter.
   *
   * @return the pension amount
   */
  public double getPension() {
    return pension;
  }

  /**
   * Sets the pension amount for the retired fighter.
   *
   * @param pension the pension amount to set
   * @return a new RetiredFighter instance with the specified pension
   */
  public void setPension(double pension) {
    if (pension < 0) {
      throw new IllegalArgumentException("pension must be greater than 0");
    }

    this.pension = pension;
  }

  /**
   * Returns the former self of the retired fighter.
   *
   * @return the former self
   */
  public Person getFormerSelf() {
    return formerSelf;
  }

  /**
   * Sets the former self of the retired fighter. This method removes the former fighter from the
   * extent.
   *
   * @param formerSelf the former fighter to set
   * @throws IllegalArgumentException if formerSelf is null
   */
  private void setFormerSelf(Person formerSelf) {

    if (formerSelf == null) {
      throw new IllegalArgumentException("formerSelf must not be null");
    }

    formerSelf.removeFromExtent();
    this.formerSelf = formerSelf;
  }

  /**
   * Sets the retirement date for the retired fighter.
   *
   * @param retirementDate the date and time of retirement
   * @throws IllegalArgumentException if retirementDate is null or in the future
   */
  private void setRetirementDate(LocalDateTime retirementDate) {

    if (retirementDate == null) {
      throw new IllegalArgumentException("retirementDate must not be null");
    }

    if (retirementDate.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("retirementDate must be after now");
    }

    this.retirementDate = retirementDate;
  }

  /**
   * Returns the retirement date of the retired fighter.
   *
   * @return the retirement date
   */
  public LocalDateTime getRetirementDate() {
    return retirementDate;
  }

  @Override
  public String toString() {
    return "RetiredFighter{"
        + "name="
        + formerSelf.getName()
        + ", surname="
        + formerSelf.getSurname()
        + ", retiredOn="
        + retirementDate
        + ", pension="
        + pension
        + '}';
  }

  @Override
  public void removeFromExtent() {
    super.removeFromExtent();
  }
}
