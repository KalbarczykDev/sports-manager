package dev.kalbarczyk.sportsmanager.competitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/competitors")
public class CompetitorRestController {
    @GetMapping
    public List<Competitor> listCompetitors() {
        return List.of();
    }
}
