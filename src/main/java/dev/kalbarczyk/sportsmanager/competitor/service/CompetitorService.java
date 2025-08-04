package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;

import java.util.List;

public interface CompetitorService {
    List<Competitor> findAll();

    Competitor findById(final Long id);

    Competitor save(final Competitor competitor);

    Competitor update(final Long id, final Competitor competitor);

    void delete(final Long id);
}
