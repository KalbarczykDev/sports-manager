package dev.kalbarczyk.sportsmanager.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    @NonNull
    @Column(nullable = false)
    private String name;
    @NonNull
    @Column(nullable = false)
    private String surname;
    private double salary;
    @NonNull
    @Column(nullable = false)
    private String country;

}
