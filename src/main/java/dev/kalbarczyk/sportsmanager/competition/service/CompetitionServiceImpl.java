package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.BaseCrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.repository.CompetitionRepository;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link CompetitionService} using {@link CompetitionRepository}
 * providing CRUD operations and additional methods for managing {@link Competition} entities.
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public final class CompetitionServiceImpl extends BaseCrudService<Competition> implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Override
    public List<Competition> findAvailableCompetitionsForCompetitor(final Competitor competitor) {
        log.info("Fetching available competitions for  competitor {}", competitor);
        val available = competitionRepository.findAllByDiscipline(competitor.getDiscipline());
        available.removeAll(competitor.getCompetitions());
        return available;
    }

    @Override
    protected JpaRepository<Competition, Long> getRepository() {
        return competitionRepository;
    }

    @Override
    protected String getEntityName() {
        return "competition";
    }


}
