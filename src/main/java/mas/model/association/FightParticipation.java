package mas.model.association;

import mas.model.Fight;
import mas.model.Fighter;
import mas.model.data.ObjectExtent;

/**
 * Represents a participation of a fighter in a fight. It contains the fighter, the fight, and the
 * points scored by the fighter in that fight.
 */
public class FightParticipation extends ObjectExtent {
  private int points;
  private Fighter fighter;
  private Fight fight;

  /**
   * Constructs a FightParticipation instance with the specified fighter and fight.
   *
   * @param fighter the fighter participating in the fight
   * @param fight the fight in which the fighter is participating
   * @throws IllegalStateException if the fighter is already participating in the specified fight
   */
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

  /**
   * Constructs a FightParticipation instance with the specified points, fighter, and fight.
   *
   * @param points the points scored by the fighter in the fight
   * @param fighter the fighter participating in the fight
   * @param fight the fight in which the fighter is participating
   */
  public int getPoints() {
    return points;
  }

  /**
   * Sets the points scored by the fighter in the fight. The points must not be negative.
   *
   * @param points the points to set
   * @throws IllegalArgumentException if the points are negative
   */
  public void setPoints(int points) {
    if (points < 0) {
      throw new IllegalArgumentException("Points cannot be negative");
    }
    this.points = points;
  }

  /**
   * Sets the fighter participating in the fight. The fighter must not be null.
   *
   * @param fighter the fighter to set
   */
  public void setFighter(Fighter fighter) {
    if (fighter != null) {
      this.fighter = fighter;
      fighter.addParticipation(this);
    }
  }

  /**
   * Gets the fighter participating in the fight.
   *
   * @return the fighter
   */
  public Fighter getFighter() {
    return fighter;
  }

  /**
   * Gets the fight in which the fighter is participating.
   *
   * @return the fight
   */
  public Fight getFight() {
    return fight;
  }

  /**
   * Sets the fight in which the fighter is participating. The fight must not be null.
   *
   * @param fight the fight to set
   */
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
