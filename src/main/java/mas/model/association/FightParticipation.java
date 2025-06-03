package mas.model.association;

import mas.model.Fight;
import mas.model.Fighter;
import mas.model.data.ObjectExtent;

public class FightParticipation extends ObjectExtent {
  private int points;
  private Fighter fighter;
  private Fight fight;

  public FightParticipation(Fighter fighter, Fight fight) {
    try {

      for (FightParticipation participation : ObjectExtent.getExtent(FightParticipation.class)) {
        if (participation.getFighter() != null
            && participation.getFighter().equals(fighter)
            && participation.getFight().equals(fight)) {
          throw new IllegalStateException("Fighter already participates in this fight");
        }
      }

      setFight(fight);
      setFighter(fighter);

    } catch (Exception e) {
      removeFromExtent();
      System.out.println("FightParticipation not created: " + e.getMessage());
    }
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    if (points < 0) {
      throw new IllegalArgumentException("Points cannot be negative");
    }
    this.points = points;
  }

  public void setFighter(Fighter fighter) {
    if (fighter != null) {
      this.fighter = fighter;
      fighter.addParticipation(this);
    }
  }

  public Fighter getFighter() {
    return fighter;
  }

  public Fight getFight() {
    return fight;
  }

  public void setFight(Fight fight) {
    if (fight != null) {
      this.fight = fight;
      fight.addParticipant(this);
    }
  }

  @Override
  public String toString() {
    return "FightParticipation{"
        + "points="
        + points
        + ", fighter="
        + fighter.getName()
        + " "
        + fighter.getSurname()
        + ", fight="
        + fight
        + '}';
  }

  @Override
  public void removeFromExtent() {
    if (fighter != null) {
      fighter.removeParticipation(this);
      fighter = null;
    }
    if (fight != null) {
      fight.removeParticipant(this);
      fight = null;
    }
    super.removeFromExtent();
  }
}
