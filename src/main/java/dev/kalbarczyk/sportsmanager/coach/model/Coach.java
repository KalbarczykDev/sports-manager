package dev.kalbarczyk.sportsmanager.coach.model;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the coach entity.
 */
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "coaches")
public class Coach extends Person {
    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    Set<Competitor> competitors = new HashSet<>();

    /**
     * Creates bidirectional relation with {@link Competitor}
     *
     * @param competitor the competitor to add
     */
    public void addCompetitor(final Competitor competitor) {
        if (competitor == null || competitors.contains(competitor)) return;
        competitors.add(competitor);
        competitor.addCoach(this);
    }

    /**
     * Removes bidirectional relation with {@link Competitor}
     *
     * @param competitor the competitor to remove
     */
    public void removeCompetitor(final Competitor competitor) {
        if (competitor == null || !competitors.contains(competitor)) return;
        competitors.remove(competitor);
        competitor.removeCoach(this);
    }

    /**
     * Returns unmodifiable Set of Competitors assigned to Coach
     *
     * @return unmodifiable set of Competitors
     */
    public Set<Competitor> getCompetitors() {
        return Collections.unmodifiableSet(competitors);
    }


}
