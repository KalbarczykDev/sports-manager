package dev.kalbarczyk.sportsmanager.shared;

public enum Discipline {
    FOOTBALL("Football"),
    BASKETBALL("Basketball"),
    VOLLEYBALL("Volleyball"),
    TENNIS("Tennis"),
    BASEBALL("Baseball"),
    CYCLING("Cycling"),
    BOXING("Boxing");

    private final String label;

    Discipline(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
