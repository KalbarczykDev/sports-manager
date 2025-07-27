package dev.kalbarczyk.sportsmanager.competitor;

import dev.kalbarczyk.sportsmanager.shared.exception.CrudException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitorService {
    private final CompetitorRepository competitorRepository;

    @Autowired
    public CompetitorService(final CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    public List<Competitor> findAll() {
        return competitorRepository.findAll();
    }

    public Competitor findById(final Long id) {
        return competitorRepository.findById(id)
                .orElseThrow(() -> new CrudException.NotFound("Competitor not found with id: " + id));
    }

    public Competitor save(final Competitor competitor) {
        return competitorRepository.save(competitor);
    }

    public Competitor update(final Long id, final Competitor competitor) {
        if (!competitorRepository.existsById(id)) {
            throw new CrudException.NotFound("Competitor not found with id: " + id);
        }
        competitor.setId(id);
        return competitorRepository.save(competitor);
    }

    public void delete(final Long id) {

        if (!competitorRepository.existsById(id)) {
            throw new CrudException.NotFound("Competitor not found with id: " + id);
        }

        competitorRepository.deleteById(id);
    }
}
