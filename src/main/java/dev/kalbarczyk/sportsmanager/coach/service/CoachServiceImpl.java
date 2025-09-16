package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.service.BaseCrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default implementation of the {@link CoachService} interface.
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CoachServiceImpl extends BaseCrudService<Coach> implements CoachService {

    private final CoachRepository coachRepository;

    @Override
    public List<Coach> findAllAvailableCoachesForCompetitor(final Competitor competitor) {

        val available = coachRepository.findAllByDiscipline(competitor.getDiscipline());
        available.removeAll(competitor.getCoaches());
        return available;
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
