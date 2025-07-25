package dev.kalbarczyk.sportsmanager.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {
    @NonNull
    private String name;
    @NonNull
    private String surname;
    private double salary;
    @NonNull
    private String country;

}
