package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;

import java.util.List;

public interface CoachService extends CrudService<Coach> {
    List<Coach> findAllAvailableCoachesForCompetitor(final Competitor competitor);

    List<Coach> findAll();
}
