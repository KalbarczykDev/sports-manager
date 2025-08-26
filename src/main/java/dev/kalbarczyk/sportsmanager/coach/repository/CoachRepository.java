package dev.kalbarczyk.sportsmanager.coach.repository;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
