package dev.kalbarczyk.sportsmanager.coach.controller;

import dev.kalbarczyk.sportsmanager.coach.service.DefaultCoachService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/coaches")
@Controller
public class CoachController {
    private final DefaultCoachService coachService;

    @Autowired
    public CoachController(final DefaultCoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public String index(final Model model) {

        val coaches = coachService;

        return "layout/layout";
    }
}
