package dev.kalbarczyk.sportsmanager.coach.controller;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.service.DefaultCoachService;
import dev.kalbarczyk.sportsmanager.common.controller.AbstractCrudController;
import dev.kalbarczyk.sportsmanager.common.service.BaseService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/coaches")
@Controller
public class CoachController extends AbstractCrudController<Coach> {
    private final DefaultCoachService coachService;
    private final PersonValidator personValidator;

    public CoachController(final DefaultCoachService coachService, final PersonValidator personValidator) {
        this.coachService = coachService;
        this.personValidator = personValidator;
    }


    @Override
    protected BaseService<Coach> getBaseService() {
        return coachService;
    }

    @Override
    protected String getModuleName() {
        return "coach";
    }

    @Override
    protected void prepareFormModel(Model model, Coach entity, String formAction, String title) {
        model.addAttribute("coach", entity);
        model.addAttribute("formAction", formAction);
        model.addAttribute("title", title);
        model.addAttribute("disciplines", Discipline.values());
        model.addAttribute("view", "modules/coach/form");
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
