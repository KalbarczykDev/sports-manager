package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        val competitors = competitorService.findAll();
        model.addAttribute("competitors", competitors);
        return "competitor/index";
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Competitor getCompetitorById(final @PathVariable Long id) {
        return competitorService.findById(id);
    }

    @PostMapping
    @ResponseBody
    public Competitor createCompetitor(@RequestBody Competitor competitor) {
        return competitorService.save(competitor);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCompetitor(final @PathVariable Long id) {
        System.out.println("Deleting competitor with ID: " + id);
        competitorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Competitor updateCompetitor(final @PathVariable Long id, final @RequestBody Competitor competitor
    ) {
        return competitorService.update(id, competitor);
    }

}
