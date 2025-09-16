package dev.kalbarczyk.sportsmanager.competition.model;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a competition entity.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "competitions")
public final class Competition extends BaseEntity {
    @ManyToMany(mappedBy = "competitions")
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Set<Competitor> competitors = new HashSet<>();
    @NonNull
    @Column(nullable = false, name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discipline")
    private Discipline discipline;

    @Column(nullable = false, name = "date")
    private LocalDate date;

    /**
     * Adds a competitor to the competition.
     *
     * @param competitor the competitor to add
     */
    public void addCompetitor(final Competitor competitor) {
        if (competitor == null || competitors.contains(competitor)) return;
        competitors.add(competitor);
        competitor.addCompetition(this);
    }

    /**
     * Removes a competitor from the competition.
     *
     * @param competitor the competitor to remove
     */
    public void removeCompetitor(final Competitor competitor) {
        if (competitor == null || !competitors.contains(competitor)) return;
        competitors.remove(competitor);
        competitor.removeCompetition(this);
    }

    /**
     * Returns an unmodifiable set of competitors in this competition.
     *
     * @return set of competitors
     */
    public Set<Competitor> getCompetitors() {
        return Collections.unmodifiableSet(competitors);
    }
}
