package mas.model;

import java.time.LocalDateTime;

import mas.model.abstraction.Person;


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
    if(pension < 0){
      throw new IllegalArgumentException("pension must be greater than 0");
    }

    this.pension = pension;
  }

  public Person getFormerSelf() {
    return formerSelf;
  }

  private void setFormerSelf(Person formerSelf) {

    if(formerSelf == null){
      throw new IllegalArgumentException("formerSelf must not be null");
    }


    formerSelf.removeFromExtent();
    this.formerSelf = formerSelf;
  }

  private void setRetirementDate(LocalDateTime retirementDate) {

    if(retirementDate == null){
      throw new IllegalArgumentException("retirementDate must not be null");
    }

    if(retirementDate.isAfter(LocalDateTime.now())){
      throw new IllegalArgumentException("retirementDate must be after now");
    }

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
