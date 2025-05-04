package mas.domain.model.sponsorship;

import mas.domain.model.fighter.Fighter;
import mas.domain.model.shared.Compensation;
import mas.domain.model.sponsor.Sponsor;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

public class Sponsorship extends ObjectExtent {
  private Fighter fighter;
  private Sponsor sponsor;
  private Compensation compensation;

  public Sponsorship(Fighter fighter, Sponsor sponsor, Compensation compensation) {
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

  public Compensation getCompensation() {
    return compensation;
  }

  public void setCompensation(Compensation compensation) {
    Util.require(compensation != null, "Compensation cannot be null");
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
