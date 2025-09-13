package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;

import java.util.List;
import java.util.Set;

public interface CompetitorService extends CrudService<Competitor> {

    void addCoach(final Long coachId, final Long competitorId);

    void removeCoach(final Long coachId, final Long competitorId);

    Set<Coach> findAllCoaches(final Long competitorId);

    List<Competitor> findAllCompetitorsByDiscipline(final Discipline discipline);
}
