package mas.model.association;

import mas.model.Fighter;
import mas.model.Sponsor;
import mas.model.data.ObjectExtent;


public class Sponsorship extends ObjectExtent {
  private Fighter fighter;
  private Sponsor sponsor;
  private double compensation;

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

  public void setFighter(Fighter fighter) {
    if (fighter != null) {
      this.fighter = fighter;
      fighter.addSponsorship(this);
    }
  }

  public Fighter getFighter() {
    return fighter;
  }

  public Sponsor getSponsor() {
    return sponsor;
  }

  public void setSponsor(Sponsor sponsor) {
    if (sponsor != null) {
      this.sponsor = sponsor;
      sponsor.addSponsorship(this);
    }
  }

  public double getCompensation() {
    return compensation;
  }

  public void setCompensation(double compensation) {
    if(compensation < 0){
     throw new IllegalArgumentException( "Compensation cannot be more than zero");
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
