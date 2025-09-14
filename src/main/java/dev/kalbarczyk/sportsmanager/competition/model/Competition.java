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

@Getter
@Setter
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "competitions")
public class Competition extends BaseEntity {
    @ManyToMany(mappedBy = "competitions")
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Set<Competitor> competitors = new HashSet<>();
    @NonNull
    @Column(nullable = false, name = "name")
    private String name;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discipline")
    private Discipline discipline;

    @NonNull
    @Column(nullable = false, name = "date")
    private LocalDate date;

    public void addCompetitor(final Competitor competitor) {
        if (competitor == null || competitors.contains(competitor)) return;
        competitors.add(competitor);
        competitor.addCompetition(this);
    }

    public void removeCompetitor(final Competitor competitor) {
        if (competitor == null || !competitors.contains(competitor)) return;
        competitors.remove(competitor);
        competitor.removeCompetition(this);
    }

    public Set<Competitor> getCompetitors() {
        return Collections.unmodifiableSet(competitors);
    }
}
