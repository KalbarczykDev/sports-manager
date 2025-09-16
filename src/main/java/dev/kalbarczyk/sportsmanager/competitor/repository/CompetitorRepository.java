package dev.kalbarczyk.sportsmanager.competitor.repository;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Competitor} entities, providing basic CRUD operations
 * and custom queries such as finding competitors by their {@link Discipline}.
 */
@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    /**
     * Finds all competitors with the specified discipline.
     *
     * @param discipline the discipline to filter by
     * @return list of competitors matching the discipline
     */
    List<Competitor> findByDiscipline(final Discipline discipline);
}
