package mas.ui.viewmodel;

import java.util.List;
import mas.model.Fight;
import mas.model.Fighter;
import mas.model.Gala;
import mas.model.association.FightParticipation;

/**
 * ManageFightsViewModel is responsible for managing the logic related to fights in the application.
 * It handles the assignment of fighters to new fights.
 */
public class ManageFightsViewModel {

  private Fight currentFight; // currently edited fight

  /**
   * Assigns a list of fighters to a new fight. This method creates a new Fight instance and
   * associates each fighter with it through FightParticipation.
   *
   * @param fighters the list of fighters to be assigned to the new fight
   */
  public void assignFightersToNewFight(List<Fighter> fighters) {

    currentFight = new Fight();

    for (Fighter fighter : fighters) {
      new FightParticipation(fighter, currentFight);
    }
    System.out.println(currentFight);
  }

  public void assignFightToGala(Fight fight, Gala gala) {
    gala.addFight(fight, 0);
  }

  public Fight getCurrentFight() {
    return currentFight;
  }

  public void setCurrentFight(Fight currentFight) {
    this.currentFight = currentFight;
  }
}
