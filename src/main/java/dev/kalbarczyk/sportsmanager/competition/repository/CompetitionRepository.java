package dev.kalbarczyk.sportsmanager.competition.repository;

import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
