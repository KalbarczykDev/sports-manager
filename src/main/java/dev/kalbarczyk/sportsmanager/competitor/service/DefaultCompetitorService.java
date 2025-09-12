package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.common.service.AbstractCrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@Primary
public class DefaultCompetitorService extends AbstractCrudService<Competitor> implements CompetitorService {
    private final CompetitorRepository competitorRepository;
    private final CoachRepository coachRepository;


    public DefaultCompetitorService(final CompetitorRepository competitorRepository, final CoachRepository coachRepository) {
        this.competitorRepository = competitorRepository;
        this.coachRepository = coachRepository;
    }

    @Override
    protected JpaRepository<Competitor, Long> getRepository() {
        return competitorRepository;
    }

    @Override
    protected String getEntityName() {
        return "Competitor";
    }

    @Override
    public void addCoach(final Long coachId, final Long competitorId) {
        var competitor = getCompetitorOrThrow(competitorId);
        var coach = getCoachOrThrow(coachId);
        competitor.addCoach(coach);
        competitorRepository.save(competitor);
    }

    @Override
    public void removeCoach(Long coachId, Long competitorId) {
        var competitor = getCompetitorOrThrow(competitorId);
        var coach = getCoachOrThrow(coachId);
        competitor.removeCoach(coach);
        competitorRepository.save(competitor);

    }

    @Override
    public Set<Coach> findAllCoaches(Long competitorId) {
        var competitor = getCompetitorOrThrow(competitorId);
        return competitor.getCoaches();
    }


    private Competitor getCompetitorOrThrow(Long competitorId) {
        return competitorRepository.findById(competitorId).orElseThrow(() -> {
            log.warn("Competitor with ID {} not found", competitorId);
            return new CrudException.InvalidEntityIdException("competitor not found");
        });
    }

    private Coach getCoachOrThrow(Long coachId) {
        return coachRepository.findById(coachId).orElseThrow(() -> {
            log.warn("Coach with ID {} not found", coachId);
            return new CrudException.InvalidEntityIdException("coach not found");
        });
    }
}
