package dev.kalbarczyk.sportsmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Competitor extends Person {
    @NonNull
    private Discipline discipline;

    private Competitor(@NonNull String name, @NonNull String surname, double salary, @NonNull String country,
                       @NonNull Discipline discipline) {
        super(name, surname, salary, country);
        this.discipline = discipline;
    }

    public static Competitor of(@NonNull String name, @NonNull String surname, double salary,
                                @NonNull String country, @NonNull Discipline discipline) {
        return new Competitor(name, surname, salary, country, discipline);
    }
}
