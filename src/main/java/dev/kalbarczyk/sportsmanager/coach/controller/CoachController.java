package dev.kalbarczyk.sportsmanager.coach.controller;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.service.DefaultCoachService;
import dev.kalbarczyk.sportsmanager.common.controller.AbstractCrudController;
import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.validation.PersonValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/coaches")
@Controller
@RequiredArgsConstructor
public class CoachController extends AbstractCrudController<Coach> {
    private final DefaultCoachService coachService;
    private final PersonValidator personValidator;
    private final CountryService countryService;
    private final CompetitorService competitorService;

    @Override
    protected CrudService<Coach> getBaseService() {
        return coachService;
    }

    @Override
    protected String getEntityNameSingular() {
        return "coach";
    }

    @Override
    protected String getEntityNamePlural() {
        return "coaches";
    }

    @Override
    protected void prepareFormModel(Model model, Coach coach, String formAction, String title) {
        model.addAttribute("coach", coach);
        model.addAttribute("countries", countryService.getCountriesForForm());
        model.addAttribute("disciplines", Discipline.values());
        model.addAttribute("formAction", formAction);
        model.addAttribute("title", title);
        model.addAttribute("view", "modules/coach/form");
    }

    @Override
    protected void addOptionalContentToModelInSHowView(Coach entity, Model model) {
        model.addAttribute("availableCompetitors", competitorService.findAllCompetitorsByDiscipline(entity.getDiscipline()));
    }

    @Override
    protected void validateEntity(Coach entity, BindingResult bindingResult) {
        personValidator.validate(entity, bindingResult);
    }

    @Override
    protected Coach createNewInstance() {
        return new Coach();
    }
}
