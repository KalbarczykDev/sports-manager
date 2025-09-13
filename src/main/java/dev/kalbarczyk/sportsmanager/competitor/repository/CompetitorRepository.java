package dev.kalbarczyk.sportsmanager.competitor.repository;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    List<Competitor> findByDiscipline(Discipline discipline);
}
