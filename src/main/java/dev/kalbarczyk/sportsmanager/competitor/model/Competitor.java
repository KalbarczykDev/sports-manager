package dev.kalbarczyk.sportsmanager.competitor.model;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the Competitor Entity.
 */
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "competitors")
public final class Competitor extends Person {

    @ManyToMany
    @JoinTable(name = "competitors_coaches", joinColumns = @JoinColumn(name = "competitor_id"), inverseJoinColumns = @JoinColumn(name = "coach_id"))
    @Builder.Default
    Set<Coach> coaches = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "competitors_competitions", joinColumns = @JoinColumn(name = "competitor_id"), inverseJoinColumns = @JoinColumn(name = "competition_id"))
    @Builder.Default
    Set<Competition> competitions = new HashSet<>();

    /**
     * Adds a coach to this competitor and updates the coach's relationship.
     *
     * @param coach the coach to add
     */
    public void addCoach(final Coach coach) {

        if (coach == null || coaches.contains(coach)) return;

        coaches.add(coach);
        coach.addCompetitor(this);
    }

    /**
     * Removes a coach from this competitor and updates the coach's relationship.
     *
     * @param coach the coach to remove
     */
    public void removeCoach(final Coach coach) {
        if (coach == null || !coaches.contains(coach)) return;
        coaches.remove(coach);
        coach.removeCompetitor(this);
    }

    /**
     * Adds a competition to this competitor and updates the competition's relationship.
     *
     * @param competition the competition to add
     */
    public void addCompetition(final Competition competition) {
        if (competition == null || competitions.contains(competition)) return;
        competitions.add(competition);
        competition.addCompetitor(this);
    }

    /**
     * Removes a competition from this competitor and updates the competition's relationship.
     *
     * @param competition the competition to remove
     */
    public void removeCompetition(final Competition competition) {
        if (competition == null || !competitions.contains(competition)) return;
        competitions.remove(competition);
        competition.removeCompetitor(this);
    }

    /**
     * Returns an unmodifiable set of coaches assigned to this competitor.
     *
     * @return set of coaches
     */
    public Set<Coach> getCoaches() {
        return Collections.unmodifiableSet(coaches);
    }

    /**
     * Returns an unmodifiable set of competitions this competitor participates in.
     *
     * @return set of competitions
     */
    public Set<Competition> getCompetitions() {
        return Collections.unmodifiableSet(competitions);
    }


}
