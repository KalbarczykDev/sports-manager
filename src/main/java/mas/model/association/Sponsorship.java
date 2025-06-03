package mas.model.association;

import mas.model.Fighter;
import mas.model.Sponsor;
import mas.model.data.ObjectExtent;

/**
 * Represents a sponsorship agreement between a fighter and a sponsor, including the compensation
 * amount.
 */
public class Sponsorship extends ObjectExtent {
  private Fighter fighter;
  private Sponsor sponsor;
  private double compensation;

  /**
   * Constructs a Sponsorship instance with the specified fighter, sponsor, and compensation.
   *
   * @param fighter the fighter being sponsored
   * @param sponsor the sponsor providing the sponsorship
   * @param compensation the amount of compensation for the sponsorship
   * @throws IllegalArgumentException if the compensation is negative
   */
  public Sponsorship(Fighter fighter, Sponsor sponsor, double compensation) {
    try {
      setSponsor(sponsor);
      setFighter(fighter);
      setCompensation(compensation);
    } catch (Exception e) {
      removeFromExtent();
      System.out.println("Sponsorship not created: " + e.getMessage());
    }
  }

  /**
   * Gets the fighter associated with this sponsorship.
   *
   * @return the fighter being sponsored
   */
  public void setFighter(Fighter fighter) {
    if (fighter != null) {
      this.fighter = fighter;
      fighter.addSponsorship(this);
    }
  }

  /**
   * Gets the fighter associated with this sponsorship.
   *
   * @return the fighter being sponsored
   */
  public Fighter getFighter() {
    return fighter;
  }

  /**
   * Gets the sponsor associated with this sponsorship.
   *
   * @return the sponsor providing the sponsorship
   */
  public Sponsor getSponsor() {
    return sponsor;
  }

  /**
   * Sets the sponsor for this sponsorship. The sponsor must not be null.
   *
   * @param sponsor the sponsor to set
   */
  public void setSponsor(Sponsor sponsor) {
    if (sponsor != null) {
      this.sponsor = sponsor;
      sponsor.addSponsorship(this);
    }
  }

  /**
   * Gets the compensation amount for this sponsorship.
   *
   * @return the compensation amount
   */
  public double getCompensation() {
    return compensation;
  }

  /**
   * Sets the compensation amount for this sponsorship. The compensation must not be negative.
   *
   * @param compensation the compensation amount to set
   * @throws IllegalArgumentException if the compensation is negative
   */
  public void setCompensation(double compensation) {
    if (compensation < 0) {
      throw new IllegalArgumentException("Compensation cannot be more than zero");
    }
    this.compensation = compensation;
  }

  @Override
  public void removeFromExtent() {
    if (fighter != null) {
      fighter.removeSponsorship(this);
      fighter = null;
    }
    if (sponsor != null) {
      sponsor.removeSponsorship(this);
      sponsor = null;
    }
    super.removeFromExtent();
  }
}
