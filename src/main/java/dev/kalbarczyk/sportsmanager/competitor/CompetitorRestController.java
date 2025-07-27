package dev.kalbarczyk.sportsmanager.competitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competitors")
public class CompetitorRestController {

    private final CompetitorService competitorService;

    @Autowired
    public CompetitorRestController(CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @GetMapping
    public List<Competitor> listCompetitors() {
        return competitorService.findAll();
    }

    @GetMapping("/{id}")
    public Competitor getCompetitorById(final @PathVariable Long id) {
        return competitorService.findById(id);
    }

    @PostMapping
    public Competitor createCompetitor(@RequestBody Competitor competitor) {
        return competitorService.save(competitor);
    }

    @DeleteMapping("/{id}")
    public void deleteCompetitor(final @PathVariable Long id) {
        competitorService.delete(id);
    }

    @PutMapping("/{id}")
    public Competitor updateCompetitor(final @PathVariable Long id, final @RequestBody Competitor competitor
    ) {
        competitor.setId(id);
        return competitorService.save(competitor);
    }

}
