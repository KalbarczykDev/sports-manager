package dev.kalbarczyk.sportsmanager.service;


import dev.kalbarczyk.sportsmanager.competitor.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.CompetitorService;
import dev.kalbarczyk.sportsmanager.shared.Discipline;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CompetitorService.class)
public class CompetitorServiceTest {

    @Autowired
    private CompetitorService competitorService;

    @Test
    void shouldSaveAndRetrieveCompetitor() {

        val name = "John";
        val surname = "Doe";
        val salary = 5000;
        val country = "USA";
        val discipline = Discipline.FOOTBALL;
        val competitor = Competitor.of(name, surname, salary, country, discipline);
        competitorService.save(competitor);

        val competitors = competitorService.findAll();

        assertThat(competitors).hasSize(1);
        assertThat(competitors.getFirst().getName()).isEqualTo("John");
    }
}
