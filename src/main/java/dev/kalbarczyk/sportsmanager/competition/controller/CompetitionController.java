package dev.kalbarczyk.sportsmanager.competition.controller;

import dev.kalbarczyk.sportsmanager.common.controller.CrudController;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.service.CompetitionService;
import dev.kalbarczyk.sportsmanager.competition.validation.CompetitionValidator;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/competitions")
@RequiredArgsConstructor
public class CompetitionController extends CrudController<Competition> {

    final private CompetitionService competitionService;
    final private CompetitionValidator competitionValidator;


    @Override
    protected void prepareFormModel(Model model, Competition competition, String formAction, String title) {
        model.addAttribute("competition", competition);
        model.addAttribute("disciplines", Discipline.values());
        model.addAttribute("formAction", formAction);
        model.addAttribute("title", title);
        model.addAttribute("view", "modules/competition/form");
    }


    @Override
    protected void addOptionalContentToModelInSHowView(Competition entity, Model model) {
        model.addAttribute("competitors", entity.getCompetitors());
    }

    @Override
    protected void validateEntity(Competition entity, BindingResult bindingResult) {
        competitionValidator.validate(entity, bindingResult);
    }

    @Override
    protected Competition createNewInstance() {
        return new Competition();
    }

    @Override
    protected CrudService<Competition> getBaseService() {
        return competitionService;
    }

    @Override
    protected String getEntityNameSingular() {
        return "competition";
    }

    @Override
    protected String getEntityNamePlural() {
        return "competitions";
    }
}
