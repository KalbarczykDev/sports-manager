package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.competitor.validation.CompetitorValidator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/competitors")
@Controller
public class CompetitorController {

    private final CompetitorService competitorService;
    private final CompetitorValidator competitorValidator;
    private final CountryService countryService;

    @Autowired
    public CompetitorController(
            final CompetitorService competitorService,
            final CompetitorValidator competitorValidator,
            final CountryService countryService) {
        this.competitorService = competitorService;
        this.competitorValidator = competitorValidator;
        this.countryService = countryService;
    }

    @GetMapping
    public String index(
            final Model model,
            final @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            final @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
    ) {
        log.info("Received request to get all competitors with sorting: sortBy={}, sortDir={}", sortBy, sortDir);

        val competitors = competitorService.findAll(sortBy, sortDir);
        model.addAttribute("competitors", competitors);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        // Variables for layout
        model.addAttribute("title", "Competitors");
        model.addAttribute("pageCss", "competitor.css"); // optional
        model.addAttribute("pageJs", "competitor.js");   // optional

        return "modules/competitor/index";
    }

    @GetMapping("{id}")
    public String show(final @PathVariable Long id, final Model model) {
        log.info("Received request to get competitors of id: {}", id);

        val competitor = competitorService.findById(id);
        model.addAttribute("competitor", competitor);

        return "modules/competitor/show";
    }

    @GetMapping("/new")
    public String showNewForm(final Model model) {
        log.info("Received request to show add competitor form");
        prepareFormModel(model, new Competitor(), "/competitors");
        return "modules/competitor/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(final @PathVariable Long id, final Model model) {
        log.info("Received request to edit competitor with id: {}", id);
        prepareFormModel(model, competitorService.findById(id), "/competitors/" + id);
        return "modules/competitor/form";
    }

    @PostMapping({"", "/{id}"})
    public String saveCompetitor(@PathVariable(required = false) Long id,
                                 @ModelAttribute Competitor competitor,
                                 BindingResult bindingResult,
                                 Model model) {
        competitorValidator.validate(competitor, bindingResult);

        if (bindingResult.hasErrors()) {
            prepareFormModel(model, competitor, id == null ? "/competitors" : "/competitors/" + id);
            return "modules/competitor/form";
        }

        if (id == null) {
            competitorService.save(competitor);
        } else {
            competitorService.update(id, competitor);
        }

        return "redirect:/competitors";
    }

    private void prepareFormModel(Model model, Competitor competitor, String formAction) {
        model.addAttribute("competitor", competitor);
        model.addAttribute("countries", countryService.getCountriesForForm());
        model.addAttribute("formAction", formAction);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCompetitor(final @PathVariable Long id) {
        log.info("Received request to delete competitor via API with id: {}", id);
        competitorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
