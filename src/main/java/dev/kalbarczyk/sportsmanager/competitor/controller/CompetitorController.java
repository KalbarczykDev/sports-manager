package dev.kalbarczyk.sportsmanager.competitor.controller;

import dev.kalbarczyk.sportsmanager.competitor.service.CompetitorService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/competitors")
@Controller
public class CompetitorController {

    private final CompetitorService competitorService;

    @Autowired
    public CompetitorController(final CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @GetMapping
    public String index(Model model) {
        val competitors = competitorService.findAll();
        model.addAttribute("competitors", competitors);
        return "competitor/index";
    }


}
