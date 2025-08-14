package dev.kalbarczyk.sportsmanager.competitor.model;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.common.model.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "competitors")
public class Competitor extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discipline")
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
