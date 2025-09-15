package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.service.BaseCrudService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
public class CoachServiceImpl extends BaseCrudService<Coach> implements CoachService {

    private final CoachRepository coachRepository;

    public CoachServiceImpl(final CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }


    @Override
    public List<Coach> findAllCoachesByDiscipline(final Discipline discipline) {
        log.info("Fetching coaches by  discipline {}", discipline);
        return coachRepository.findAllByDiscipline(discipline);
    }

    @Override
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }

    @Override
    protected JpaRepository<Coach, Long> getRepository() {
        return coachRepository;
    }


    @Override
    protected String getEntityName() {
        return "Coach";
    }


}
