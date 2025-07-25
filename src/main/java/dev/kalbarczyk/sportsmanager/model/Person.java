package dev.kalbarczyk.sportsmanager.model;

import dev.kalbarczyk.sportsmanager.model.attributes.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Person {
    private String name;
    private String surname;
    private double salary;
    private Address address;
}
