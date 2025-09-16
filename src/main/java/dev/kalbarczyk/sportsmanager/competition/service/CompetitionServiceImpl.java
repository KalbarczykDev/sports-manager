package dev.kalbarczyk.sportsmanager.competition.service;

import dev.kalbarczyk.sportsmanager.common.service.BaseCrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.repository.CompetitionRepository;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CompetitionServiceImpl extends BaseCrudService<Competition> implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Override
    public List<Competition> findAllCompetitionsByDiscipline(final Discipline discipline) {
        log.info("Fetching competitions by  discipline {}", discipline);
        return competitionRepository.findAllByDiscipline(discipline);
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
