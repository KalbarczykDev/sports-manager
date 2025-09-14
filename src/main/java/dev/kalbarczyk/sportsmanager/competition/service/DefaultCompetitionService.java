package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.AbstractCrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DefaultCompetitionService extends AbstractCrudService<Competition> implements CompetitionService {

    @Override
    protected JpaRepository<Competition, Long> getRepository() {
        return null;
    }

    @Override
    protected String getEntityName() {
        return "competition";
    }
}
