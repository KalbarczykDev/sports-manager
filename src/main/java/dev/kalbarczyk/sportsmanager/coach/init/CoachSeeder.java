package dev.kalbarczyk.sportsmanager.coach.init;

import dev.kalbarczyk.sportsmanager.coach.model.Coach;
import dev.kalbarczyk.sportsmanager.coach.repository.CoachRepository;
import dev.kalbarczyk.sportsmanager.person.init.AbstractPersonSeeder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class CoachSeeder extends AbstractPersonSeeder<Coach> implements CommandLineRunner {
    private final CoachRepository coachRepository;

    @Value("${sportsmanager.seeder.competitors:100}")
    private int seedCount;

    @Override
    protected int getSeedCount() {
        return seedCount;
    }

    @Override
    protected long count() {
        return coachRepository.count();
    }

    @Override
    protected void save(Coach entity) {
        coachRepository.save(entity);
    }

    @Override
    protected Coach createRandomPerson() {
        return Coach.builder()
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

