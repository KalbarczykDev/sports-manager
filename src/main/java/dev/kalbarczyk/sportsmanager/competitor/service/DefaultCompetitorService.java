package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.common.service.AbstractBaseService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class DefaultCompetitorService extends AbstractBaseService<Competitor> implements CompetitorService {
    private final CompetitorRepository competitorRepository;


    public DefaultCompetitorService(final CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }


    @Override
    protected JpaRepository<Competitor, Long> getRepository() {
        return competitorRepository;
    }
}
