package dev.kalbarczyk.sportsmanager.competitor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitorService {
    private final CompetitorRepository competitorRepository;

    public CompetitorService(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    public List<Competitor> findAll() {
        return competitorRepository.findAll();
    }

    public Competitor save(Competitor competitor) {
        return competitorRepository.save(competitor);
    }

    public void delete(Long id) {
        competitorRepository.deleteById(id);
    }
}
