package dev.kalbarczyk.sportsmanager.competition.init;

import dev.kalbarczyk.sportsmanager.common.init.EntitySeeder;
import dev.kalbarczyk.sportsmanager.competition.model.Competition;
import dev.kalbarczyk.sportsmanager.competition.repository.CompetitionRepository;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompetitionSeeder extends EntitySeeder<Competition> implements CommandLineRunner {

    private final CompetitionRepository competitionRepository;

    @Value("${sportsmanager.seeder.competitors:10}")
    private int seedCount;

    @Override
    protected int getSeedCount() {
        return seedCount;
    }

    @Override
    protected long count() {
        return competitionRepository.count();
    }

    @Override
    protected void save(Competition entity) {
        competitionRepository.save(entity);
    }

    @Override
    protected Competition createRandomEntity() {
        val randomDiscipline = Discipline.values()[faker.random().nextInt(Discipline.values().length)];
        return Competition.builder()
                .name(faker.company().name() + " " + "CUP")
                .discipline(randomDiscipline)
                .date(faker.timeAndDate().future(365, java.util.concurrent.TimeUnit.DAYS)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate())
                .build();
    }

    @Override
    public void run(String... args) {
        seed();
    }
}
