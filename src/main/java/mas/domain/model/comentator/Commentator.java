package mas.domain.model.comentator;

import mas.domain.model.contractor.Contractor;
import mas.domain.model.fight.Fight;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commentator extends ObjectExtent {
    private final List<Fight> fights = new ArrayList<>();
    private Language language;

    private Contractor contractor;


    private Commentator(Contractor contractor, Language language) {
        try {
            setContractor(contractor);
            setLanguage(language);
        } catch (Exception e) {
            System.out.println("Commentator not created: " + e.getMessage());
            removeFromExtent();
        }
    }

    public static Commentator of(Contractor contractor, Language language) {
        return new Commentator(contractor, language);
    }

    public void setContractor(Contractor contractor) {
        Util.require(contractor != null, "Contractor cannot be null");
        this.contractor = contractor;
    }

    public void removeContractor() {
        if (contractor != null) {
            contractor = null;
        }
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void addFight(Fight fight) {
        Util.require(fight != null, "Fight cannot be null");
        if (!fights.contains(fight)) {
            fights.add(fight);
            fight.addCommentator(this);
        }
    }

    public void removeFight(Fight fight) {
        if (fights.contains(fight)) {
            fights.remove(fight);
            fight.removeCommentator(this);
        }
    }

    public List<Fight> getFights() {
        return Collections.unmodifiableList(fights);
    }

    public void setLanguage(Language language) {
        Util.require(language != null, "Language cannot be null");
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Commentator{" +
                "fights=" + fights +
                ", language=" + language +
                '}';
    }

    @Override
    public void removeFromExtent() {
        removeFromFights();
        removeFromContractor();
        super.removeFromExtent();
    }

    private void removeFromFights() {
        for (Fight fight : fights) {
            fight.removeCommentator(this);
        }
        fights.clear();
    }

    private void removeFromContractor() {
        if (contractor != null) {
            contractor.removeCommentatorRole();
        }
    }
}
