package dev.kalbarczyk.sportsmanager.competitor.init;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompetitorSeeder implements CommandLineRunner {
    private final CompetitorRepository competitorRepository;

    @Override
    public void run(String... args) {
        if (competitorRepository.count() == 0) {
            log.info("Seeding initial competitors data...");
            competitorRepository.save(
                    Competitor.of("John", "Doe",
                            50000, "USA", Discipline.FOOTBALL));
            competitorRepository.save(
                    Competitor.of("Jane", "Smith",
                            60000, "Canada", Discipline.BASKETBALL));
            competitorRepository.save(
                    Competitor.of("Alice", "Johnson",
                            55000, "UK", Discipline.VOLLEYBALL));
            log.info("Seeded initial competitors data complete!");
        }
    }
}
