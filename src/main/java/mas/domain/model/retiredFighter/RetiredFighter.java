package mas.domain.model.retiredFighter;

import mas.domain.model.person.Person;
import mas.util.Util;

import java.time.LocalDateTime;

public class RetiredFighter extends Person {
    private Person formerSelf;
    private Pension pension;
    private RetirementDate retirementDate;

    public RetiredFighter(Person formerSelf, Pension pension) {
        try {
            setName(formerSelf.getName());
            setSurname(formerSelf.getSurname());
            setAddress(formerSelf.getAddress());
            setRetirementDate(RetirementDate.of(LocalDateTime.now()));
            setPension(pension);
            setFormerSelf(formerSelf);
        } catch (Exception e) {
            removeFromExtent();
            System.out.println("RetiredFighter not created: " + e.getMessage());
        }

    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        Util.require(pension != null, "pension cannot be null");
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

    private void setRetirementDate(RetirementDate retirementDate) {
        Util.require(retirementDate != null, "retirementDate cannot be null");
        this.retirementDate = retirementDate;
    }

    public RetirementDate getRetirementDate() {
        return retirementDate;
    }

    @Override
    public String toString() {
        return "RetiredFighter{" +
                "name=" + formerSelf.getName() +
                ", surname=" + formerSelf.getSurname() +
                ", retiredOn=" + retirementDate +
                ", pension=" + pension +
                '}';
    }

    @Override
    public void removeFromExtent() {
        super.removeFromExtent();
    }
}
