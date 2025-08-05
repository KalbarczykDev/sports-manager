package dev.kalbarczyk.sportsmanager.competitor.init;

import dev.kalbarczyk.sportsmanager.common.enums.Discipline;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompetitorSeeder implements CommandLineRunner {
    private final CompetitorRepository competitorRepository;

    @Value("${sportsmanager.seeder.count:10}")
    private int seedCount = 10;

    @Override
    public void run(final String... args) {
        if (competitorRepository.count() == 0) {
            log.info("Seeding initial competitors data...");

            for (int i = 0; i < seedCount; i++) {
                val faker = new Faker();
                val firstName = faker.name().firstName();
                val lastName = faker.name().lastName();
                val score = faker.number().numberBetween(2000, 20000);
                val country = faker.address().country();
                val discipline = Discipline.values()[faker.number().numberBetween(0, Discipline.values().length)];

                competitorRepository.save(
                        Competitor.of(firstName, lastName, score, country, discipline));
            }
            log.info("Seeded initial competitors data complete!");
        }
    }
}
