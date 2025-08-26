package dev.kalbarczyk.sportsmanager.competitor.init;

import dev.kalbarczyk.sportsmanager.competitor.model.Competitor;
import dev.kalbarczyk.sportsmanager.competitor.repository.CompetitorRepository;
import dev.kalbarczyk.sportsmanager.person.init.AbstractPersonSeeder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompetitorSeeder extends AbstractPersonSeeder<Competitor> implements CommandLineRunner {
    private final CompetitorRepository competitorRepository;


    @Override
    protected long count() {
        return competitorRepository.count();
    }

    @Override
    protected void save(Competitor entity) {
        competitorRepository.save(entity);
    }

    @Override
    protected Competitor createRandomPerson() {
        return Competitor.builder()
                .name(randomFirstName())
                .surname(randomLastName())
                .salary(randomSalary(2000, 20000))
                .country(randomCountry())
                .discipline(randomDiscipline())
                .build();
    }

    @Override
    public void run(String... args) {
        seed();
    }
}
