package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.person.validation.PersonValidator;
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
    private final PersonValidator competitorValidator;
    private final CountryService countryService;

    @Autowired
    public CompetitorController(
            final CompetitorService competitorService,
            final PersonValidator competitorValidator,
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
        model.addAttribute("view", "modules/competitor/index");
        return "layout/layout";
    }

    @GetMapping("{id}")
    public String show(final @PathVariable Long id, final Model model) {
        log.info("Received request to get competitors of id: {}", id);

        val competitor = competitorService.findById(id);
        model.addAttribute("competitor", competitor);
        model.addAttribute("view", "modules/competitor/show");
        return "layout/layout";
    }

    @GetMapping("/new")
    public String showNewForm(final Model model) {
        log.info("Received request to show add competitor form");
        prepareFormModel(model, new Competitor(),
                "/competitors", "New competitor");
        return "layout/layout";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(final @PathVariable Long id, final Model model) {
        log.info("Received request to edit competitor with id: {}", id);
        prepareFormModel(model, competitorService.findById(id),
                "/competitors/" + id, "Edit competitor");
        return "layout/layout";
    }

    @PostMapping({"", "/{id}"})
    public String saveCompetitor(@PathVariable(required = false) Long id,
                                 @ModelAttribute Competitor competitor,
                                 BindingResult bindingResult,
                                 Model model) {
        competitorValidator.validate(competitor, bindingResult);

        if (bindingResult.hasErrors()) {

            final String uri;
            final String title;

            if (id == null) {
                title = "New competitor";
                uri = "/competitors";
            } else {
                title = "Edit competitor";
                uri = "/competitors/" + id;
            }
            prepareFormModel(model, competitor, uri, title);
            return "layout/layout";
        }

        if (id == null) {
            competitorService.save(competitor);
        } else {
            competitorService.update(id, competitor);
        }

        return "redirect:/competitors";
    }

    private void prepareFormModel(Model model, Competitor competitor, String formAction, String title) {
        model.addAttribute("competitor", competitor);
        model.addAttribute("countries", countryService.getCountriesForForm());
        model.addAttribute("formAction", formAction);
        model.addAttribute("title", title);
        model.addAttribute("view", "modules/competitor/form");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCompetitor(final @PathVariable Long id) {
        log.info("Received request to delete competitor via API with id: {}", id);
        competitorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
