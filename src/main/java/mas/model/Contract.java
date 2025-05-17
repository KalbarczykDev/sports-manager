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

  private Contract(
      Fighter fighter, LocalDateTime signedAt, LocalDateTime expiresAt, boolean isPernament) {
    try {

      setFighter(fighter);
      setSignedAt(signedAt);
      setPermanent(isPernament);
      setExpiresAt(expiresAt);

    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Contract not created: " + e.getMessage());
    }
  }

  public static Contract createContract(
      Fighter fighter, LocalDateTime signedAt, boolean isPermanent, LocalDateTime expiresAt) {
    validateXOR(expiresAt, isPermanent);
    return new Contract(fighter, signedAt, expiresAt, isPermanent);
  }

  private static void validateXOR(LocalDateTime expiresAt, boolean isPermanent) {
    if (isPermanent && expiresAt != null) {
      throw new IllegalArgumentException("Contract cannot be both permanent and expiring.");
    }
    if (!isPermanent && expiresAt == null) {
      throw new IllegalArgumentException(
          "Contract must be either permanent or have an expiration date.");
    }
  }

  private void setExpiresAt(LocalDateTime expiresAt) {

    if (expiresAt == null) {
      throw new IllegalArgumentException("expiresAt cannot be null.");
    }

    if (expiresAt.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("expiresAt cannot be in the past.");
    }

    this.expiresAt = expiresAt;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  private void setPermanent(boolean isPermanent) {
    this.isPermanent = isPermanent;
  }

  public boolean isPermanent() {
    return isPermanent;
  }

  public LocalDateTime getSignedAt() {
    return signedAt;
  }

  private void setSignedAt(LocalDateTime signedAt) {
    if (signedAt == null) {
      throw new IllegalArgumentException("signedAt cannot be null.");
    }

    this.signedAt = signedAt;
  }

  public Fighter getFighter() {
    return fighter;
  }

  private void setFighter(Fighter fighter) {
    if (fighter == null) {
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
