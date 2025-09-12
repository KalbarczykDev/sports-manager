package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.coach.service.CoachService;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/competitors")
@Controller
public class CompetitorController extends AbstractCrudController<Competitor> {

    private final CompetitorService competitorService;
    private final PersonValidator competitorValidator;
    private final CountryService countryService;
    private final CoachService coachService;

    @Autowired
    public CompetitorController(
            final CompetitorService competitorService,
            final PersonValidator competitorValidator,
            final CountryService countryService, CoachService coachService) {
        this.competitorService = competitorService;
        this.competitorValidator = competitorValidator;
        this.countryService = countryService;
        this.coachService = coachService;
    }

    @PutMapping("/{competitorId}/coaches/{coachId}")
    @ResponseBody
    public ResponseEntity<Void> addCoach(final @PathVariable Long competitorId, @PathVariable Long coachId) {
        competitorService.addCoach(coachId, competitorId);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{competitorId}/coaches/{coachId}")
    @ResponseBody
    public ResponseEntity<Void> removeCoach(
            final @PathVariable Long competitorId,
            final @PathVariable Long coachId
    ) {
        competitorService.removeCoach(coachId, competitorId);
        return ResponseEntity.noContent().build();
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
