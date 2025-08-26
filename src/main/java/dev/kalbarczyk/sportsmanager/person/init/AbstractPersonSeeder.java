package dev.kalbarczyk.sportsmanager.person.init;


import dev.kalbarczyk.sportsmanager.common.init.AbstractEntitySeeder;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPersonSeeder<T extends Person> extends AbstractEntitySeeder<T> {
    protected abstract T createRandomPerson();

    @Override
    protected T createRandomEntity() {
        return createRandomPerson();
    }

    protected final String randomFirstName() {
        return faker.name().firstName();
    }

    protected final String randomLastName() {
        return faker.name().lastName();
    }

    protected final int randomSalary(final int min, final int max) {
        return faker.number().numberBetween(min, max);
    }

    protected final String randomCountry() {
        return faker.address().country();
    }

    protected final Discipline randomDiscipline() {
        return Discipline.values()[faker.number().numberBetween(0, Discipline.values().length)];
    }

}
