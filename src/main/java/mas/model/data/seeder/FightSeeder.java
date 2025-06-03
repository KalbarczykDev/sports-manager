package mas.model.data.seeder;

import java.util.List;
import mas.model.Fight;
import mas.model.Fighter;
import mas.model.Gala;
import mas.model.association.FightParticipation;
import mas.model.data.ObjectExtent;

public class FightSeeder implements ISeeder {

  @Override
  public void run() {
    List<Fighter> fighters = ObjectExtent.getExtent(Fighter.class);

    Fighter toMany = fighters.getFirst();
    toMany.setName("ToMany");
    toMany.setSurname("Fights");

    System.out.println("Changed fighter name to 'ToMany Fights'");

    for (int i = 0; i < 10; i++) {
      Fight fight = new Fight();
      new FightParticipation(toMany, fight);
      Gala gala = ObjectExtent.getExtent(Gala.class).getFirst();
      gala.addFight(fight, i);
    }
  }
}
