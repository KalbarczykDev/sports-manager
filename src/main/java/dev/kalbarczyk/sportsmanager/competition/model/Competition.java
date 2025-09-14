package dev.kalbarczyk.sportsmanager.competition.model;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity {
    @ManyToMany(mappedBy = "competitions")
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


}
