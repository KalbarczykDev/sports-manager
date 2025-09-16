package dev.kalbarczyk.sportsmanager.person.init;


import dev.kalbarczyk.sportsmanager.common.init.EntitySeeder;
import dev.kalbarczyk.sportsmanager.person.enums.Discipline;
import dev.kalbarczyk.sportsmanager.person.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Abstract seeder for entities extending {@link Person}.
 * Provides helper methods for generating random person attributes.
 *
 * @param <T> the type of {@link Person} to seed
 */
@Slf4j
@RequiredArgsConstructor
public abstract class PersonSeeder<T extends Person> extends EntitySeeder<T> {
    /**
     * Creates a random instance of the entity extending  person.
     *
     * @return a new random {@link T} entity extending  person
     */
    protected abstract T createRandomPerson();

    @Override
    protected T createRandomEntity() {
        return createRandomPerson();
    }

    /**
     * Generates a random first name.
     */
    protected final String randomFirstName() {
        return faker.name().firstName();
    }

    /**
     * Generates a random last name.
     */
    protected final String randomLastName() {
        return faker.name().lastName();
    }

    /**
     * Generates a random salary between min and max (inclusive).
     *
     * @param min minimum salary
     * @param max maximum salary
     * @return random salary
     */
    protected final int randomSalary(final int min, final int max) {
        return faker.number().numberBetween(min, max);
    }

    /**
     * Generates a random country name.
     */
    protected final String randomCountry() {
        return faker.address().country();
    }

    /**
     * Picks a random {@link Discipline}.
     */
    protected final Discipline randomDiscipline() {
        return Discipline.values()[faker.number().numberBetween(0, Discipline.values().length)];
    }

}
