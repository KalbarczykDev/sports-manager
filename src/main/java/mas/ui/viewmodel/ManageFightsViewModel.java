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

  /**
   * Assigns a specific fight to a gala. This method adds the fight to the gala's list of fights.
   *
   * @param fight the fight to be assigned to the gala
   * @param gala the gala to which the fight will be assigned
   */
  public void assignFightToGala(Fight fight, Gala gala) {
    gala.addFight(fight, 0);
  }

  /**
   * Returns the currently edited fight.
   *
   * @return the current fight
   */
  public Fight getCurrentFight() {
    return currentFight;
  }

  /**
   * Sets the current fight to be edited.
   *
   * @param currentFight the fight to be set as the current fight
   */
  public void setCurrentFight(Fight currentFight) {
    this.currentFight = currentFight;
  }
}
