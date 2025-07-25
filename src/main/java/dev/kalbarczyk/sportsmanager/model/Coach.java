package dev.kalbarczyk.sportsmanager.model;

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
