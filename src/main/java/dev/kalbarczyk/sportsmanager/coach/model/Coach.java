package dev.kalbarczyk.sportsmanager.coach.model;

import dev.kalbarczyk.sportsmanager.person.model.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "coaches")
public class Coach extends Person {
}
