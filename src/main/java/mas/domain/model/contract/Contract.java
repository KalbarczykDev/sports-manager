package mas.domain.model.contract;

import java.util.List;
import java.util.Objects;
import mas.domain.model.fighter.Fighter;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

public class Contract extends ObjectExtent {
  private SigningDate signedAt;
  private Fighter fighter;

  private boolean isPermanent = false;
  private ExpirationDate expiresAt;

  private Contract(Fighter fighter, SigningDate signedAt) {
    try {
      setFighter(fighter);
      setSignedAt(signedAt);

    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Contract not created: " + e.getMessage());
    }
  }

  public void setExpiresAt(ExpirationDate expiresAt) {

    if (isPermanent) {
      throw new IllegalStateException("Contract is already set to pernament.");
    }

    Util.require(expiresAt != null, "Expiration date cannot be null");
    this.expiresAt = expiresAt;
  }

  public void setIsPernament(boolean isPernament) {

    if (expiresAt != null) {
      throw new IllegalStateException("Contract Expiration date is already set");
    }

    this.isPermanent = isPernament;
  }

  public static Contract createContact(Fighter fighter, SigningDate signedAt) {
    return new Contract(fighter, signedAt);
  }

  public SigningDate getSignedAt() {
    return signedAt;
  }

  public void setSignedAt(SigningDate signedAt) {
    Util.require(signedAt != null, "Signed date cannot be null");
    this.signedAt = signedAt;
  }

  public ExpirationDate getExpiresAt() {
    return expiresAt;
  }

  public Fighter getFighter() {
    return fighter;
  }

  private void setFighter(Fighter fighter) {
    Util.require(fighter != null, "Fighter cannot be null");

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
