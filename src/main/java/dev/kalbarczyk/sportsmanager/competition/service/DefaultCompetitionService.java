package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.BaseCrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class DefaultCompetitionService extends BaseCrudService<Competition> implements CompetitionService {

    private final CompetitionRepository competitionRepository;


    @Override
    protected JpaRepository<Competition, Long> getRepository() {
        return competitionRepository;
    }

    @Override
    protected String getEntityName() {
        return "competition";
    }
}
