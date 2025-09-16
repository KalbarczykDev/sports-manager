package dev.kalbarczyk.sportsmanager.competitor.service;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.service.CoachService;
import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.common.service.BaseCrudService;
import dev.kalbarczyk.sportsmanager.competition.service.CompetitionService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CompetitorServiceImpl extends BaseCrudService<Competitor> implements CompetitorService {
    private final CompetitorRepository competitorRepository;
    private final CoachService coachService;
    private final CompetitionService competitionService;

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
        val competitor = findById(competitorId);
        val coach = coachService.findById(coachId);

        if (competitor.getDiscipline() != coach.getDiscipline()) {
            throw new CrudException.RelationRequirementsException("Coach must practice the same discipline as Competitor");
        }
        competitor.addCoach(coach);
        competitorRepository.save(competitor);
    }

    @Override
    public void removeCoach(Long coachId, Long competitorId) {
        val competitor = findById(competitorId);
        val coach = coachService.findById(coachId);
        competitor.removeCoach(coach);
        competitorRepository.save(competitor);

    }

    @Override
    public void addCompetition(Long competitionId, Long competitorId) {
        val competitor = findById(competitorId);
        val competition = competitionService.findById(competitionId);

        if (competitor.getDiscipline() != competition.getDiscipline()) {
            throw new CrudException.RelationRequirementsException("Discipline for competition and competitor must be the same");
        }

        competitor.addCompetition(competition);
        competitorRepository.save(competitor);
    }

    @Override
    public void removeCompetition(Long competitionId, Long competitorId) {
        val competitor = findById(competitorId);
        val competition = competitionService.findById(competitionId);
        competitor.removeCompetition(competition);
        competitorRepository.save(competitor);
    }

    @Override
    public List<Competitor> findAllCompetitorsByDiscipline(Discipline discipline) {
        return competitorRepository.findByDiscipline(discipline);
    }

    @Override
    public Set<Coach> findAllCoaches(Long competitorId) {
        var competitor = findById(competitorId);
        return competitor.getCoaches();
    }

}
