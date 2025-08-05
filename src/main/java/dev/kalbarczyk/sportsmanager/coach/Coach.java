package dev.kalbarczyk.sportsmanager.coach;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.common.model.Person;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Coach extends Person {
    @NonNull
    private Discipline discipline;
    private int yearsOfExperience;
}
