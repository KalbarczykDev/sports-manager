package mas.model;

import java.time.LocalDateTime;

import mas.util.Util;

public class RetiredFighter extends Person {
  private Person formerSelf;
  private double pension;
  private LocalDateTime retirementDate;

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

  public double getPension() {
    return pension;
  }

  public void setPension(double pension) {
    Util.require(pension > 0, "pension must be greater than 0");
    this.pension = pension;
  }

  public Person getFormerSelf() {
    return formerSelf;
  }

  private void setFormerSelf(Person formerSelf) {
    Util.require(formerSelf != null, "formerSelf cannot be null");
    formerSelf.removeFromExtent();
    this.formerSelf = formerSelf;
  }

  private void setRetirementDate(LocalDateTime retirementDate) {
    Util.require(retirementDate != null, "retirementDate cannot be null");
     Util.require(!retirementDate.isBefore(LocalDateTime.now()), "Expiration date cannot be in the future");
    this.retirementDate = retirementDate;
  }

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
