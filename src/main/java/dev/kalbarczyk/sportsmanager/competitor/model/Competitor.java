package dev.kalbarczyk.sportsmanager.competitor.model;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import jakarta.persistence.*;
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
@Table(name = "competitors")
public class Competitor extends Person {

    @ManyToMany
    @JoinTable(
            name = "competitors_coaches",
            joinColumns = @JoinColumn(name = "competitor_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    @Builder.Default
    Set<Coach> coaches = new HashSet<>();

    public void addCoach(final Coach coach) {

        if (coach == null || coaches.contains(coach)) return;

        coaches.add(coach);
        coach.addCompetitor(this);
    }


    public void removeCoach(final Coach coach) {
        if (coach == null || !coaches.contains(coach)) return;
        coaches.remove(coach);
        coach.removeCompetitor(this);
    }

    public void clearCoaches() {
        for (val coach : new HashSet<>(coaches)) {
            removeCoach(coach);
        }
        coaches.clear();
    }


    public Set<Coach> getCoaches() {
        return Collections.unmodifiableSet(coaches);
    }


}
