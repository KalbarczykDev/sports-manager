package mas.domain.model.fight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.domain.model.comentator.Commentator;
import mas.domain.model.fightParticipation.FightParticipation;
import mas.domain.model.fighter.Fighter;
import mas.domain.model.gala.Gala;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

public class Fight extends ObjectExtent {
  private Fighter winner;
  private final List<FightParticipation> participants = new ArrayList<>();
  private final List<Commentator> commentators = new ArrayList<>();
  private Gala gala;

  private int fightPriority;

  public Fight() {}

  public int getFightPriority() {
    return fightPriority;
  }

  public void setFightPriority(int fightNumber) {

    this.fightPriority = fightNumber;
  }

  public void addFighter(Fighter fighter) {
    new FightParticipation(fighter, this);
  }

  public void addParticipant(FightParticipation fightParticipation) {
    Util.require(fightParticipation != null, "FightParticipation cannot be null");
    if (!participants.contains(fightParticipation)) {
      participants.add(fightParticipation);
    }
  }

  public void removeParticipant(FightParticipation fightParticipation) {
    participants.remove(fightParticipation);
  }

  public List<FightParticipation> getParticipants() {
    return Collections.unmodifiableList(participants);
  }

  public void addCommentator(Commentator commentator) {
    Util.require(commentator != null, "Commentator cannot be null");
    if (!commentators.contains(commentator)) {
      commentators.add(commentator);
      commentator.addFight(this);
    }
  }

  public void removeCommentator(Commentator commentator) {
    if (commentators.contains(commentator)) {
      commentators.remove(commentator);
      commentator.removeFight(this);
    }
  }

  public void setGala(Gala gala) {
    Util.require(gala != null, "Gala cannot be null");

    if (this.gala != null) {
      this.gala.removeFight(this);
    }
    this.gala = gala;
  }

  public Gala getGala() {
    return gala;
  }

  public void removeGala() {
    if (gala != null) {
      gala.removeFight(this);
      gala = null;
    }
  }

  public List<Commentator> getCommentators() {
    return Collections.unmodifiableList(commentators);
  }

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

  public void deleteFightParticipations() {
    for (FightParticipation fightParticipation : participants) {
      fightParticipation.removeFromExtent();
    }
    participants.clear();
  }

  private void removeFromCommentators() {
    for (Commentator commentator : commentators) {
      commentator.removeFight(this);
    }
    commentators.clear();
  }
}
