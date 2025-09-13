package dev.kalbarczyk.sportsmanager.auth.model;

public record LoginRequest(
        String email, String password
) {
}
