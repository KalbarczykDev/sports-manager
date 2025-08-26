package dev.kalbarczyk.sportsmanager.person.model;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person extends BaseEntity {
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
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discipline")
    private Discipline discipline;
}
