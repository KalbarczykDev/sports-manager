package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.competitor.exception.CompetitorException;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.competitor.validation.CompetitorValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/competitors")
public class CompetitorRestController {
    private final CompetitorService competitorService;
    private final CompetitorValidator competitorValidator;

    @Autowired
    public CompetitorRestController(final CompetitorService competitorService, final CompetitorValidator competitorValidator) {
        this.competitorService = competitorService;
        this.competitorValidator = competitorValidator;
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
    public ResponseEntity<Competitor> createCompetitor(final @RequestBody Competitor competitor, final BindingResult bindingResult) {
        log.info("Received request to create competitor via API: {}", competitor);

        competitorValidator.validate(competitor, bindingResult);

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors found: {}", bindingResult.getAllErrors());

            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            throw new CompetitorException.Invalid(errors);
        }

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
