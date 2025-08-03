package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/competitors")
@Controller
public class CompetitorController {

    private final CompetitorService competitorService;

    @Autowired
    public CompetitorController(final CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @GetMapping
    public String index(Model model) {
        log.info("Received request to get all competitors");
        val competitors = competitorService.findAll();
        model.addAttribute("competitors", competitors);
        return "competitor/index";
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Competitor getCompetitorById(final @PathVariable Long id) {
        log.info("Received request to get competitor by id: {}", id);
        return competitorService.findById(id);
    }

    @PostMapping
    @ResponseBody
    public Competitor createCompetitor(@RequestBody Competitor competitor) {
        log.info("Received request to create competitor: {}", competitor);
        return competitorService.save(competitor);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCompetitor(final @PathVariable Long id) {
        log.info("Received request to delete competitor with id: {}", id);
        competitorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Competitor updateCompetitor(final @PathVariable Long id, final @RequestBody Competitor competitor
    ) {
        log.info("Received request to update competitor with id: {}", id);
        return competitorService.update(id, competitor);
    }

}
