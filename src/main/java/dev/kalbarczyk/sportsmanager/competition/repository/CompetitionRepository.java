package dev.kalbarczyk.sportsmanager.competition.repository;

import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link Competition} entities.
 */
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    /**
     * Finds all competitions by the specified discipline.
     *
     * @param discipline the discipline to filter by
     * @return list of competitions in the given discipline
     */
    List<Competition> findAllByDiscipline(final Discipline discipline);
}
