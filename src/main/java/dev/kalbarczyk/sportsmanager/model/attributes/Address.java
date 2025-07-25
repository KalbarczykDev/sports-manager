package dev.kalbarczyk.sportsmanager.model.attributes;

public record Address(
        int streetNumber,
        String streetName,
        String city,
        String state,
        String zip
) {
}
