package dev.kalbarczyk.sportsmanager.competitor.init;

import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.competition.repository.CompetitionRepository;
import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import dev.kalbarczyk.sportsmanager.person.init.PersonSeeder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class CompetitorSeeder extends PersonSeeder<Competitor> implements CommandLineRunner {
    private final CompetitorRepository competitorRepository;
    private final CoachRepository coachRepository;
    private final Random random = new Random();
    private final CompetitionRepository competitionRepository;

    @Value("${sportsmanager.seeder.competitors:100}")
    private int seedCount;

    @Override
    protected int getSeedCount() {
        return seedCount;
    }

    @Override
    protected long count() {
        return competitorRepository.count();
    }

    @Override
    protected void save(Competitor entity) {
        competitorRepository.save(entity);
    }

    @Override
    @Transactional
    protected Competitor createRandomPerson() {


        val competitor = Competitor.builder().name(randomFirstName()).surname(randomLastName()).salary(randomSalary(2000, 20000)).country(randomCountry()).discipline(randomDiscipline()).build();

        val allCoaches = coachRepository.findAll();
        if (!allCoaches.isEmpty()) {
            Collections.shuffle(allCoaches, random);
            int assignCount = random.nextInt(Math.min(5, allCoaches.size()) + 1);
            for (int i = 0; i < assignCount; i++) {
                if (competitor.getDiscipline().equals(allCoaches.get(i).getDiscipline())) {
                    competitor.addCoach(allCoaches.get(i));
                }

            }
        }

        val allCompetitions = competitionRepository.findAll();
        if (!allCompetitions.isEmpty()) {
            Collections.shuffle(allCompetitions, random);
            int assignCount = random.nextInt(Math.min(5, allCompetitions.size()) + 1);
            for (int i = 0; i < assignCount; i++) {
                if (competitor.getDiscipline().equals(allCompetitions.get(i).getDiscipline())) {
                    competitor.addCompetition(allCompetitions.get(i));
                }

            }
        }

        return competitor;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seed();
    }
}
