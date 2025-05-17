package mas.model;

import mas.model.data.ObjectExtent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commentator extends ObjectExtent {
    private final List<Fight> fights = new ArrayList<>();
    private String language;

    private Contractor contractor;


    private Commentator(Contractor contractor, String language) {
        try {
            setContractor(contractor);
            setLanguage(language);
        } catch (Exception e) {
            System.out.println("Commentator not created: " + e.getMessage());
            removeFromExtent();
        }
    }

    public static Commentator of(Contractor contractor, String language) {
        return new Commentator(contractor, language);
    }

    public void setContractor(Contractor contractor) {
        if (contractor == null) {
            throw new IllegalArgumentException("Contractor cannot be null");
        }
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
        if(fight == null) {
            throw new IllegalArgumentException("Fight cannot be null");
        }
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

    public void setLanguage(String language) {
        if (language == null || language.isEmpty()) {
            throw new IllegalArgumentException("Language cannot be null or empty");
        }
        this.language = language;
    }

    public String getLanguage() {
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
