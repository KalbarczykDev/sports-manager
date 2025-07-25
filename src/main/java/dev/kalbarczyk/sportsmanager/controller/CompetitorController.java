package dev.kalbarczyk.sportsmanager.controller;

import dev.kalbarczyk.sportsmanager.model.Competitor;
import dev.kalbarczyk.sportsmanager.model.Discipline;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/competitors")
@Controller
public class CompetitorController {

    @GetMapping
    public String index(Model model) {

        List<Competitor> competitors = List.of(
                Competitor.of("John", "Doe", 50000, "USA", Discipline.FOOTBALL),
                Competitor.of("Jane", "Smith", 60000, "Canada", Discipline.BASKETBALL),
                Competitor.of("Alice", "Johnson", 55000, "UK", Discipline.VOLLEYBALL)
        );

        model.addAttribute("competitors", competitors);


        return "competitor/index";
    }

    @PostMapping("/add")
    public ResponseEntity<Competitor> addCompetitor(Competitor competitor) {
        return ResponseEntity.ok(competitor);
    }

}
