package dev.kalbarczyk.sportsmanager.competitor.model;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.common.model.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Competitor extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Discipline discipline;

    private Competitor(final @NonNull String name, final @NonNull String surname, final double salary, final @NonNull String country,
                       final @NonNull Discipline discipline) {
        super(name, surname, salary, country);
        this.discipline = discipline;
    }

    public static Competitor of(final @NonNull String name, final @NonNull String surname, final double salary,
                                final @NonNull String country, final @NonNull Discipline discipline) {
        return new Competitor(name, surname, salary, country, discipline);
    }
}
