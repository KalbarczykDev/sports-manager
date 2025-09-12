package dev.kalbarczyk.sportsmanager.coach.model;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.val;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "coaches")
public class Coach extends Person {
    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    Set<Competitor> competitors = new HashSet<>();

    public void addCompetitor(final Competitor competitor) {
        if (competitor == null || competitors.contains(competitor)) return;
        competitors.add(competitor);
        competitor.addCoach(this);
    }

    public void removeCompetitor(final Competitor competitor) {
        if (competitor == null || !competitors.contains(competitor)) return;
        competitors.remove(competitor);
        competitor.removeCoach(this);
    }

    public void clearCompetitors() {
        for (val competitor : new HashSet<>(competitors)) {
            removeCompetitor(competitor);
        }
        competitors.clear();
    }

    public Set<Competitor> getCompetitors() {
        return Collections.unmodifiableSet(competitors);
    }


}
