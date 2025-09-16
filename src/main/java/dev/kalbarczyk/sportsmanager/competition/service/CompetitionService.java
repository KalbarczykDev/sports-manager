package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;

import java.util.List;

/**
 * Service interface for managing {@link Competition} entities.
 */
public interface CompetitionService extends CrudService<Competition> {
    /**
     * Finds all competitions that a given competitor can participate in.
     *
     * @param competitor the competitor to check availability for
     * @return list of available competitions
     */
    List<Competition> findAvailableCompetitionsForCompetitor(final Competitor competitor);
}
