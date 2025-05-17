package mas.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import mas.model.data.ObjectExtent;

public class Contract extends ObjectExtent {

  private Fighter fighter;

  private boolean isPermanent = false;
  private LocalDateTime expiresAt;
  private LocalDateTime signedAt;

  private Contract(Fighter fighter, LocalDateTime signedAt) {
    try {
      setFighter(fighter);
      setSignedAt(signedAt);

    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Contract not created: " + e.getMessage());
    }
  }

  public void setExpiresAt(LocalDateTime expiresAt) {

    if (isPermanent) {
      throw new IllegalStateException("Contract is already set to pernament.");
    }

    if(expiresAt == null) {
      throw new IllegalArgumentException("expiresAt cannot be null.");
    }

    if(expiresAt.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("expiresAt cannot be in the past.");
    }



    this.expiresAt = expiresAt;
  }

  public void setIsPernament(boolean isPernament) {

    if (expiresAt != null) {
      throw new IllegalStateException("Contract Expiration date is already set");
    }

    this.isPermanent = isPernament;
  }

  public static Contract createContact(Fighter fighter, LocalDateTime signedAt) {
    return new Contract(fighter, signedAt);
  }

  public LocalDateTime getSignedAt() {
    return signedAt;
  }

  public void setSignedAt(LocalDateTime signedAt) {
    if(signedAt == null) {
      throw new IllegalArgumentException("signedAt cannot be null.");
    }

    this.signedAt = signedAt;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public Fighter getFighter() {
    return fighter;
  }

  private void setFighter(Fighter fighter) {
    if(fighter == null) {
      throw new IllegalArgumentException("fighter cannot be null.");
    }


    List<Contract> contracts = fighter.getContracts();

    if (contracts.contains(this)) {
      return;
    }

    fighter.addContract(this);
    this.fighter = fighter;
  }

  @Override
  public void removeFromExtent() {
    removeFromFighter();
    super.removeFromExtent();
  }

  private void removeFromFighter() {
    if (fighter != null) {
      fighter.removeContract(this);
    }
    fighter = null;
  }

  @Override
  public String toString() {
    return "Contract{"
        + "signedAt="
        + signedAt
        + ", "
        + (isPermanent
            ? "PERMANENT"
            : expiresAt != null ? "expiresAt=" + expiresAt : "Expiration not negotiated")
        + ", fighter="
        + (fighter != null ? fighter.getName() + " " + fighter.getSurname() : "null")
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Contract contract)) return false;
    return Objects.equals(signedAt, contract.signedAt)
        && Objects.equals(expiresAt, contract.expiresAt)
        && Objects.equals(fighter, contract.fighter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(signedAt, expiresAt, fighter);
  }
}
