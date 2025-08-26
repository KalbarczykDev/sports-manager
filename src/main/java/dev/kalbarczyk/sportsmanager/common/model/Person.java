package dev.kalbarczyk.sportsmanager.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false, name = "id")
    private Long id;
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
