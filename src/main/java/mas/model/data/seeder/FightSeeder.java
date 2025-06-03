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

    for (int i = 0; i < 10; i++) {
      Fight fight = new Fight();
      Fighter another = fighters.get(i + 2);
      new FightParticipation(toMany, fight);
      new FightParticipation(another, fight);
      Gala gala = ObjectExtent.getExtent(Gala.class).get(Random(0, 2));
      gala.addFight(fight, i);
    }
  }

  private int Random(int i, int j) {
    return (int) (Math.random() * (j - i + 1)) + i;
  }
}
