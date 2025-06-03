package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.model.association.FightParticipation;
import mas.model.data.ObjectExtent;

/**
 * Represents a fight in the system, which involves multiple fighters and can have a winner. A fight
 * can also have commentators and is associated with a gala event.
 */
public class Fight extends ObjectExtent {
  private Fighter winner;
  private final List<FightParticipation> participants = new ArrayList<>();
  private final List<Contractor> commentators = new ArrayList<>();
  private Gala gala;

  private int fightPriority;

  /** Gets the fight priority, which is used to determine the order of fights in a gala. */
  public int getFightPriority() {
    return fightPriority;
  }

  /**
   * Sets the fight priority, which is used to determine the order of fights in a gala. The priority
   * is represented by an integer value.
   *
   * @param fightNumber the priority number for the fight
   */
  public void setFightPriority(int fightNumber) {

    this.fightPriority = fightNumber;
  }

  /**
   * Adds a fighter to the fight.
   *
   * @param fighter the fighter to add to the fight
   */
  public void addParticipant(FightParticipation fightParticipation) {
    if (fightParticipation == null) {
      throw new IllegalArgumentException("fightParticipation cannot be null");
    }

    if (!participants.contains(fightParticipation)) {
      participants.add(fightParticipation);
    }
  }

  /**
   * Removes a fighter from the fight.
   *
   * @param fightParticipation the fight participation to remove
   */
  public void removeParticipant(FightParticipation fightParticipation) {
    participants.remove(fightParticipation);
  }

  /**
   * Returns an unmodifiable list of participants in the fight.
   *
   * @return an unmodifiable list of FightParticipation objects representing the fighters in the
   *     fight
   */
  public List<FightParticipation> getParticipants() {
    return Collections.unmodifiableList(participants);
  }

  /**
   * Adds a commentator to the fight.
   *
   * @param commentator the contractor who is a commentator
   * @throws IllegalArgumentException if the commentator is null or not of type Commentator
   */
  public void addCommentator(Contractor commentator) {
    if (commentator == null) {
      throw new IllegalArgumentException("commentator cannot be null");
    }

    if (!commentator.getContractorTypes().contains(ContractorType.Commentator)) {
      throw new IllegalArgumentException("Contractor is not Commentator");
    }

    if (!commentators.contains(commentator)) {
      commentators.add(commentator);
      commentator.addFight(this);
    }
  }

  /**
   * Removes a commentator from the fight.
   *
   * @param commentator the contractor who is a commentator
   */
  public void removeCommentator(Contractor commentator) {
    if (commentators.contains(commentator)) {
      commentators.remove(commentator);
      commentator.removeFight(this);
    }
  }

  /**
   * Sets the gala associated with this fight.
   *
   * @param gala the gala to associate with this fight
   * @throws IllegalArgumentException if the gala is null
   */
  public void setGala(Gala gala) {

    if (gala == null) {
      throw new IllegalArgumentException("gala cannot be null");
    }

    if (this.gala != null) {
      this.gala.removeFight(this);
    }
    this.gala = gala;
  }

  /**
   * Returns the gala associated with this fight.
   *
   * @return the gala associated with this fight, or null if not associated with any gala
   */
  public Gala getGala() {
    return gala;
  }

  /**
   * Removes the association of this fight with its gala. This method should be called when the
   * fight is no longer part of a gala.
   */
  public void removeGala() {
    if (gala != null) {
      gala.removeFight(this);
      gala = null;
    }
  }

  /**
   * Returns an unmodifiable list of commentators for the fight.
   *
   * @return an unmodifiable list of Contractor objects representing the commentators
   */
  public List<Contractor> getCommentators() {
    return Collections.unmodifiableList(commentators);
  }

  /**
   * Sets the winner of the fight. The winner must be one of the participants in the fight.
   *
   * @param winner the fighter who won the fight
   * @throws IllegalArgumentException if the winner is not a participant in the fight
   */
  public void setWinner(Fighter winner) {
    if (winner == null) {
      this.winner = null;
    }

    for (FightParticipation fightParticipation : participants) {
      if (fightParticipation.getFighter() == winner) {
        this.winner = winner;
        return;
      }
    }
    throw new IllegalArgumentException("Fighter not participating in the fight");
  }

  /**
   * Returns the winner of the fight.
   *
   * @return the Fighter who won the fight, or null if no winner has been set
   */
  public Fighter getWinner() {
    return winner;
  }

  @Override
  public String toString() {
    return "Fight{"
        + "winner="
        + (winner != null ? winner.getName() + " " + winner.getSurname() : "No winner yet")
        + ", participants="
        + participants.stream()
            .map(p -> p.getFighter().getName() + " " + p.getFighter().getSurname())
            .toList()
        + '}';
  }

  @Override
  protected void removeFromExtent() {
    deleteFightParticipations();
    removeFromCommentators();
    super.removeFromExtent();
  }

  /**
   * Deletes all fight participations associated with this fight. This method should be called when
   * the fight is removed from the extent to ensure that all associated fight participations are
   * also removed.
   */
  public void deleteFightParticipations() {
    for (FightParticipation fightParticipation : participants) {
      fightParticipation.removeFromExtent();
    }
    participants.clear();
  }

  /**
   * Removes all commentators associated with this fight. This method should be called when the
   * fight is removed from the extent to ensure that all associated commentators are also removed.
   */
  private void removeFromCommentators() {
    for (Contractor commentator : commentators) {
      commentator.removeFight(this);
    }
    commentators.clear();
  }
}
