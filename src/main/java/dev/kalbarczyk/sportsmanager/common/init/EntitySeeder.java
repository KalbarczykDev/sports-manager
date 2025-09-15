package dev.kalbarczyk.sportsmanager.common.init;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

@Slf4j
public abstract class EntitySeeder<T extends BaseEntity> {

    protected Faker faker = new Faker();

    protected abstract int getSeedCount();

    protected abstract long count();

    protected abstract void save(final T entity);

    protected abstract T createRandomEntity();

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
