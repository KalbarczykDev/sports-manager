package dev.kalbarczyk.sportsmanager.common.init;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

/**
 * Generic abstract class for seeding initial data for entities extending {@link BaseEntity}.
 *
 * @param <T> the type of the entity to seed
 */
@Slf4j
public abstract class EntitySeeder<T extends BaseEntity> {

    protected Faker faker = new Faker();

    /**
     * Returns the number of entities to seed.
     *
     * @return the seed count
     */
    protected abstract int getSeedCount();

    /**
     * Returns the current number of entities already persisted.
     *
     * @return the entity count
     */
    protected abstract long count();

    /**
     * Saves a given entity to the persistence layer.
     *
     * @param entity the entity to save
     */
    protected abstract void save(final T entity);

    /**
     * Creates a random instance of the entity.
     *
     * @return a randomly generated entity
     */
    protected abstract T createRandomEntity();

    /**
     * Seeds the initial data if none exists.
     */
    public final void seed() {
        if (count() == 0) {
            log.info("Seeding initial {} data...", getClass().getSimpleName());

            for (int i = 0; i < getSeedCount(); i++) {
                save(createRandomEntity());
            }

            log.info("Seeded initial {} data complete!", getClass().getSimpleName());
        }
    }
}
