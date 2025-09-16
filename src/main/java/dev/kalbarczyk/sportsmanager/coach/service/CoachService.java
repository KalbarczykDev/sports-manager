package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;

import java.util.List;

/**
 * Service interface for managing {@link Coach} entities.
 */
public interface CoachService extends CrudService<Coach> {

    /**
     * Returns all coaches that are available for assignment to the given competitor.
     *
     * @param competitor the competitor to find available coaches for
     * @return list of available coaches
     */
    List<Coach> findAllAvailableCoachesForCompetitor(final Competitor competitor);

    /**
     * Returns all coaches in the database
     *
     * @return list of all coaches.
     */
    List<Coach> findAll();
}
