package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;

import java.util.List;

public interface CompetitionService extends CrudService<Competition> {
    List<Competition> findAvailableCompetitionsForCompetitor(final Competitor competitor);
}
