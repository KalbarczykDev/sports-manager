package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;

import java.util.List;

public interface CompetitionService extends CrudService<Competition> {
    List<Competition> findAllCompetitionsByDiscipline(final Discipline discipline);
}
