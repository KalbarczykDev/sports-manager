package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.common.controller.AbstractCrudController;
import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping("/competitors")
@Controller
public class CompetitorController extends AbstractCrudController<Competitor> {

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

    @PutMapping("/{competitorId}/coaches/{coachId}")
    @ResponseBody
    public ResponseEntity<Void> addCoach(final @PathVariable Long competitorId, @PathVariable Long coachId) {
        competitorService.addCoach(coachId, competitorId);
        return ResponseEntity.status(204).build();
    }


    @Override
    protected void prepareFormModel(Model model, Competitor competitor, String formAction, String title) {
        model.addAttribute("competitor", competitor);
        model.addAttribute("coaches", competitor.getCoaches());
        model.addAttribute("countries", countryService.getCountriesForForm());
        model.addAttribute("disciplines", Discipline.values());
        model.addAttribute("formAction", formAction);
        model.addAttribute("title", title);
        model.addAttribute("view", "modules/competitor/form");
    }

    @Override
    protected void validateEntity(Competitor entity, BindingResult bindingResult) {
        competitorValidator.validate(entity, bindingResult);
    }

    @Override
    protected Competitor createNewInstance() {
        return new Competitor();
    }

    @Override
    protected CrudService<Competitor> getBaseService() {
        return competitorService;
    }

    @Override
    protected String getEntityNameSingular() {
        return "competitor";
    }

    @Override
    protected String getEntityNamePlural() {
        return "competitors";
    }

}
