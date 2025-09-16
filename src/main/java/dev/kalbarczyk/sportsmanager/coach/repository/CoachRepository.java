package dev.kalbarczyk.sportsmanager.coach.repository;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for performing CRUD operations on {@link Coach} entities.
 */
@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    /**
     * Finds all coaches with the specified {@link Discipline}.
     *
     * @param discipline the discipline to filter by
     * @return list of coaches with the given discipline
     */
    @Query("select c from Coach c where c.discipline = ?1")
    List<Coach> findAllByDiscipline(final Discipline discipline);
}
