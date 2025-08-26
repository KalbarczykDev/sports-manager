package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;

import java.util.List;

public interface CoachService {
    List<Coach> findAll(final String sortBy, final String sortDir);

    Coach findById(final Long id);

    Coach save(final Competitor competitor);

    Coach update(final Long id, final Coach coach);

    void delete(final Long id);
}
