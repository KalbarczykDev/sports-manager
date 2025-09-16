package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;

import java.util.List;
import java.util.Set;

/**
 * Service interface for managing {@link Competitor} entities,
 * including assignment of coaches and competitions.
 */
public interface CompetitorService extends CrudService<Competitor> {


    /**
     * Assigns a coach to a competitor.
     */
    void addCoach(final Long coachId, final Long competitorId);

    /**
     * Removes a coach from a competitor.
     */
    void removeCoach(final Long coachId, final Long competitorId);

    /**
     * Assigns a competition to a competitor.
     */
    void addCompetition(final Long competitionId, final Long competitorId);

    /**
     * Removes a competition from a competitor.
     */
    void removeCompetition(final Long competitionId, final Long competitorId);

    /**
     * Returns all coaches assigned to a competitor.
     */
    Set<Coach> findAllCoaches(final Long competitorId);

    /**
     * Finds all competitors with the specified discipline.
     */
    List<Competitor> findAllCompetitorsByDiscipline(final Discipline discipline);
}
