package mas.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import mas.model.data.ObjectExtent;

/**
 * Represents a contract between a fighter and an organization. A contract can be either permanent
 * or have an expiration date. It includes the fighter, the date it was signed, and the expiration
 * date if applicable.
 */
public class Contract extends ObjectExtent {

  private Fighter fighter;

  private boolean isPermanent = false;
  private LocalDateTime expiresAt;
  private LocalDateTime signedAt;

  /**
   * Constructs a Contract with the specified fighter, signed date, expiration date, and permanence.
   *
   * @param fighter the fighter associated with the contract
   * @param signedAt the date and time when the contract was signed
   * @param expiresAt the expiration date and time of the contract, or null if permanent
   * @param isPernament true if the contract is permanent, false if it has an expiration date
   */
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

  /**
   * Factory method to create a new contract.
   *
   * @param fighter the fighter associated with the contract
   * @param signedAt the date and time when the contract was signed
   * @param isPermanent true if the contract is permanent, false if it has an expiration date
   * @param expiresAt the expiration date and time of the contract, or null if permanent
   * @return a new Contract instance
   */
  public static Contract createContract(
      Fighter fighter, LocalDateTime signedAt, boolean isPermanent, LocalDateTime expiresAt) {
    validateXOR(expiresAt, isPermanent);
    return new Contract(fighter, signedAt, expiresAt, isPermanent);
  }

  /**
   * Factory method to create a permanent contract.
   *
   * @param fighter the fighter associated with the contract
   * @param signedAt the date and time when the contract was signed
   * @return a new permanent Contract instance
   */
  private static void validateXOR(LocalDateTime expiresAt, boolean isPermanent) {
    if (isPermanent && expiresAt != null) {
      throw new IllegalArgumentException("Contract cannot be both permanent and expiring.");
    }
    if (!isPermanent && expiresAt == null) {
      throw new IllegalArgumentException(
          "Contract must be either permanent or have an expiration date.");
    }
  }

  /**
   * Sets the expiration date for the contract.
   *
   * @param expiresAt the expiration date and time of the Contract
   * @throws IllegalArgumentException if expiresAt is null or in the PasteAction
   */
  private void setExpiresAt(LocalDateTime expiresAt) {

    if (expiresAt == null) {
      throw new IllegalArgumentException("expiresAt cannot be null.");
    }

    if (expiresAt.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("expiresAt cannot be in the past.");
    }

    this.expiresAt = expiresAt;
  }

  /**
   * Returns the expiration date of the contract.
   *
   * @return the expiration date and time of the contract, or null if it is permanent
   */
  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  /**
   * Sets whether the contract is permanent.
   *
   * @param isPermanent true if the contract is permanent, false if it has an expiration date
   */
  private void setPermanent(boolean isPermanent) {
    this.isPermanent = isPermanent;
  }

  /**
   * Returns whether the contract is permanent.
   *
   * @return true if the contract is permanent, false if it has an expiration date
   */
  public boolean isPermanent() {
    return isPermanent;
  }

  /**
   * Returns the date and time when the contract was signed.
   *
   * @return the date and time when the contract was signed
   */
  public LocalDateTime getSignedAt() {
    return signedAt;
  }

  /**
   * Sets the date and time when the contract was signed.
   *
   * @param signedAt the date and time when the contract was signed
   * @throws IllegalArgumentException if signedAt is null
   */
  private void setSignedAt(LocalDateTime signedAt) {
    if (signedAt == null) {
      throw new IllegalArgumentException("signedAt cannot be null.");
    }

    this.signedAt = signedAt;
  }

  /**
   * Returns the fighter associated with the contract.
   *
   * @return the fighter associated with the contract
   */
  public Fighter getFighter() {
    return fighter;
  }

  /**
   * Sets the fighter associated with the contract.
   *
   * @param fighter the fighter to associate with the contract
   * @throws IllegalArgumentException if fighter is null
   */
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

  /**
   * Removes the contract from the fighter it is associated with. This method is called when the
   * contract is removed from the extent.
   */
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
