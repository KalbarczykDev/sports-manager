package dev.kalbarczyk.sportsmanager.person.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Discipline {
    FOOTBALL("Football"),
    BASKETBALL("Basketball"),
    VOLLEYBALL("Volleyball"),
    TENNIS("Tennis"),
    BASEBALL("Baseball"),
    CYCLING("Cycling"),
    BOXING("Boxing");
    private final String label;
}
