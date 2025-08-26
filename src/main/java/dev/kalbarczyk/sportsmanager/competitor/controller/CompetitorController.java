package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.common.controller.AbstractCrudController;
import dev.kalbarczyk.sportsmanager.common.service.BaseService;
import dev.kalbarczyk.sportsmanager.common.service.CountryService;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.validation.PersonValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

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
    protected void validateEntity(Competitor entity, BindingResult bindingResult) {
        competitorValidator.validate(entity, bindingResult);
    }

    @Override
    protected Competitor createNewInstance() {
        return new Competitor();
    }

    @Override
    protected BaseService<Competitor> getBaseService() {
        return competitorService;
    }

    @Override
    protected String getModuleName() {
        return "competitor";
    }
}
