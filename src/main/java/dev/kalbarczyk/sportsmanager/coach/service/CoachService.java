package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;

import java.util.List;

public interface CoachService extends CrudService<Coach> {
    List<Coach> findAllCoachesByDiscipline(final Discipline discipline);

    List<Coach> findAll();
}
