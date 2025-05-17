package mas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mas.model.association.FightParticipation;
import mas.model.data.ObjectExtent;

public class Fight extends ObjectExtent {
  private Fighter winner;
  private final List<FightParticipation> participants = new ArrayList<>();
  private final List<Contractor> commentators = new ArrayList<>();
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
    if (fightParticipation == null) {
      throw new IllegalArgumentException("fightParticipation cannot be null");
    }

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

  public void removeCommentator(Contractor commentator) {
    if (commentators.contains(commentator)) {
      commentators.remove(commentator);
      commentator.removeFight(this);
    }
  }

  public void setGala(Gala gala) {

    if (gala == null) {
      throw new IllegalArgumentException("gala cannot be null");
    }

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

  public List<Contractor> getCommentators() {
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
    for (Contractor commentator : commentators) {
      commentator.removeFight(this);
    }
    commentators.clear();
  }
}
