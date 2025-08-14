package dev.kalbarczyk.sportsmanager.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Person {
    @NonNull
    @Column(nullable = false, name = "name")
    private String name;
    @NonNull
    @Column(nullable = false, name = "surname")
    private String surname;
    @Min(0)
    @Column(name = "salary")
    private double salary;
    @NonNull
    @Column(nullable = false, name = "country")
    private String country;

}
