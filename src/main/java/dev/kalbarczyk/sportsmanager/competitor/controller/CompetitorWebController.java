package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/competitors")
@Controller
public class CompetitorWebController {

    private final CompetitorService competitorService;

    @Autowired
    public CompetitorWebController(final CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @GetMapping
    public String index(final Model model) {
        log.info("Received request to get all competitors");
        val competitors = competitorService.findAll();
        model.addAttribute("competitors", competitors);
        return "competitor/index";
    }

    @GetMapping("/new")
    public String showNewForm(final Model model) {
        log.info("Received request to show add competitor form");
        model.addAttribute("competitor", new Competitor());
        return "competitor/new";
    }

    @PostMapping
    public String createCompetitorFromForm(final @ModelAttribute Competitor competitor) {
        log.info("Received request to create competitor: {}", competitor);
        competitorService.save(competitor);
        return "redirect:/competitors";
    }


}
