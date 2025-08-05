package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/competitors")
public class CompetitorRestController {
    private final CompetitorService competitorService;

    @Autowired
    public CompetitorRestController(final CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @GetMapping
    public ResponseEntity<List<Competitor>> getAllCompetitors() {
        log.info("Received request to get all competitors via API");
        return ResponseEntity.ok(competitorService.findAll());
    }

    @GetMapping("/{id}")
    public Competitor getCompetitorById(final @PathVariable Long id) {
        log.info("Received request to get competitor via API by id: {}", id);
        return competitorService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Competitor> createCompetitor(@RequestBody Competitor competitor) {
        log.info("Received request to create competitor via API: {}", competitor);

        Competitor savedCompetitor = competitorService.save(competitor);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCompetitor.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCompetitor);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Competitor updateCompetitor(final @PathVariable Long id, final @RequestBody Competitor competitor
    ) {
        log.info("Received request to update competitor via API with id: {}", id);
        return competitorService.update(id, competitor);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCompetitor(final @PathVariable Long id) {
        log.info("Received request to delete competitor via API with id: {}", id);
        competitorService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
