package dev.kalbarczyk.sportsmanager.competition.controller;

import dev.kalbarczyk.sportsmanager.common.controller.AbstractCrudController;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller("/competitions")
@RequiredArgsConstructor
public class CompetitionController extends AbstractCrudController<Competition> {

    final CompetitionService competitionService;


    @Override
    protected void prepareFormModel(Model model, Competition entity, String formAction, String title) {

    }


    @Override
    protected void addOptionalContentToModelInSHowView(Competition entity, Model model) {

    }

    @Override
    protected void validateEntity(Competition entity, BindingResult bindingResult) {

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
