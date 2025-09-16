package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.coach.service.CoachService;
import dev.kalbarczyk.sportsmanager.common.controller.CrudController;
import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competition.service.CompetitionService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/competitors")
@Controller
@RequiredArgsConstructor
public class CompetitorController extends CrudController<Competitor> {

    private final CompetitorService competitorService;
    private final PersonValidator personValidator;
    private final CountryService countryService;
    private final CoachService coachService;
    private final CompetitionService competitionService;

    @PutMapping("/{competitorId}/coaches/{coachId}")
    @ResponseBody
    public ResponseEntity<Void> addCoach(final @PathVariable Long competitorId, @PathVariable Long coachId) {
        competitorService.addCoach(coachId, competitorId);
        return ResponseEntity.status(201).build();
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

    @PutMapping("/{competitorId}/competitions/{competitionId}")
    @ResponseBody
    public ResponseEntity<Void> addCompetition(final @PathVariable Long competitorId, @PathVariable Long competitionId) {
        competitorService.addCompetition(competitionId, competitorId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{competitorId}/coaches/{competitionId}")
    @ResponseBody
    public ResponseEntity<Void> removeCompetition(
            final @PathVariable Long competitorId,
            final @PathVariable Long competitionId
    ) {
        competitorService.removeCompetition(competitionId, competitorId);
        return ResponseEntity.noContent().build();
    }


    @Override
    protected void prepareFormModel(Model model, Competitor competitor, String formAction, String title) {
        model.addAttribute("competitor", competitor);
        model.addAttribute("countries", countryService.getCountriesForForm());
        model.addAttribute("disciplines", Discipline.values());
        model.addAttribute("formAction", formAction);
        model.addAttribute("title", title);
        model.addAttribute("view", "modules/competitor/form");
    }

    @Override
    protected void addOptionalContentToModelInSHowView(final Competitor competitor, final Model model) {
        model.addAttribute("availableCoaches", coachService.findAllCoachesByDiscipline(competitor.getDiscipline()));
        model.addAttribute("availableCompetitions", competitionService.findAllCompetitionsByDiscipline(competitor.getDiscipline()));
    }

    @Override
    protected void validateEntity(Competitor entity, BindingResult bindingResult) {
        personValidator.validate(entity, bindingResult);
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
