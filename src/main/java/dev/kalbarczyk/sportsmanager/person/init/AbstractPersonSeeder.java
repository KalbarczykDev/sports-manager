package dev.kalbarczyk.sportsmanager.person.init;


import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPersonSeeder<T extends Person> {

    @Value("${sportsmanager.seeder.count:100}")
    protected int seedCount;

    protected final Faker faker = new Faker();
    protected final Random random = new Random();

    protected abstract long count();

    protected abstract void save(T entity);

    protected abstract T createRandomPerson();

    public void seed() {
        if (count() == 0) {
            log.info("Seeding initial {} data...", getClass().getSimpleName());

            for (int i = 0; i < seedCount; i++) {
                save(createRandomPerson());
            }

            log.info("Seeded initial {} data complete!", getClass().getSimpleName());
        }
    }

    protected String randomFirstName() {
        return faker.name().firstName();
    }

    protected String randomLastName() {
        return faker.name().lastName();
    }

    protected int randomSalary(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    protected String randomCountry() {
        return faker.address().country();
    }

    protected Discipline randomDiscipline() {
        return Discipline.values()[faker.number().numberBetween(0, Discipline.values().length)];
    }

}
