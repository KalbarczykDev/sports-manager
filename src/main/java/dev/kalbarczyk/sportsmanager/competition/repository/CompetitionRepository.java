package dev.kalbarczyk.sportsmanager.competition.repository;

import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    List<Competition> findAllByDiscipline(Discipline discipline);
}
