package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.competitor.validation.CompetitorValidator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/competitors")
@Controller
public class CompetitorWebController {

    private final CompetitorService competitorService;
    private final CompetitorValidator competitorValidator;
    private final CountryService countryService;

    @Autowired
    public CompetitorWebController(
            final CompetitorService competitorService,
            final CompetitorValidator competitorValidator,
            final CountryService countryService) {
        this.competitorService = competitorService;
        this.competitorValidator = competitorValidator;
        this.countryService = countryService;
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
        model.addAttribute("countries", countryService.getCountriesForForm());
        return "competitor/new";
    }

    @PostMapping
    public String createCompetitorFromForm(final @ModelAttribute Competitor competitor, final BindingResult bindingResult, final Model model) {
        log.info("Received request to create competitor: {}", competitor);

        competitorValidator.validate(competitor, bindingResult);

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors found: {}", bindingResult.getAllErrors());
            model.addAttribute("countries", countryService.getCountriesForForm());
            model.addAttribute("competitor", competitor);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "competitor/new";
        }

        competitorService.save(competitor);
        return "redirect:/competitors";
    }


}
