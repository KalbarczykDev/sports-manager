package dev.kalbarczyk.sportsmanager.coach.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class DefaultCoachService extends AbstractCrudService<Coach> implements CoachService {

    private final CoachRepository coachRepository;

    public DefaultCoachService(final CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
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
