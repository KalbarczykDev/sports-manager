package mas.domain.model.fightParticipation;

import mas.domain.model.fight.Fight;
import mas.domain.model.fighter.Fighter;
import mas.infrastructure.repository.ObjectExtent;

public class FightParticipation extends ObjectExtent {
    private Points points;
    private Fighter fighter;
    private Fight fight;


    public FightParticipation(Fighter fighter, Fight fight) {
        try {
            setFight(fight);
            setFighter(fighter);
        } catch (Exception e) {
            removeFromExtent();
        }
    }

    public Points getPoints() {
        return points;
    }

    public void setPoints(Points points) {
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
        return "FightParticipation{" +
                "points=" + points +
                ", fighter=" + fighter.getName() + " " + fighter.getSurname() +
                ", fight=" + fight+
                '}';
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
