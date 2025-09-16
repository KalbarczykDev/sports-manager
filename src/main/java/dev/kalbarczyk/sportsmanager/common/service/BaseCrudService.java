package dev.kalbarczyk.sportsmanager.common.service;

import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mapping.PropertyReferenceException;

/**
 * Abstract base service providing standard CRUD operations for entities extending {@link BaseEntity}.
 *
 * @param <T> the type of entity
 */
@Slf4j
public abstract class BaseCrudService<T extends BaseEntity> implements CrudService<T> {

    /**
     * Returns the JPA repository for the entity.
     *
     * @return repository instance
     */
    protected abstract JpaRepository<T, Long> getRepository();

    /**
     * Returns the human-readable name of the entity, used in logs and exceptions.
     *
     * @return entity name
     */
    protected abstract String getEntityName();

    @Override
    public Page<T> findAll(final int page, final int size, final String sortBy, final String sortDir) {
        log.info("Fetching {} objects of type {} on page {} sorted by {} direction {}", size, this.getClass().getSimpleName(), page, sortBy, sortDir);
        try {
            val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
            return getRepository().findAll(pageable);
        } catch (PropertyReferenceException e) {
            throw new CrudException.InvalidSortingArgument(e.getMessage());
        }
    }

    @Override
    public T findById(final Long id) {
        log.info("Fetching object of type {}", this.getEntityName());
        return getRepository().findById(id).orElseThrow(() -> {
            log.warn("Object {} not found with id {}", this.getEntityName(), id);
            return new CrudException.NotFound(this.getEntityName());
        });
    }

    @Override
    public T save(final T entity) {
        log.info("Saving object of type {}", this.getEntityName());
        return getRepository().save(entity);
    }

    @Override
    public T update(final Long id, final T entity) {
        log.info("Updating object of type {} and id {}", this.getEntityName(), id);
        if (!getRepository().existsById(id)) {
            log.warn("Cannot update. Object {} not found with ID: {}", this.getEntityName(), id);
            throw new CrudException.NotFound("Competitor not found with id: " + id);
        }
        entity.setId(id);
        return getRepository().save(entity);
    }

    @Override
    public void delete(final Long id) {
        log.info("Deleting {} with ID: {}", this.getEntityName(), id);
        if (!getRepository().existsById(id)) {
            throw new CrudException.NotImplementedEntityException("Entity not found with id: " + id);
        }
        getRepository().deleteById(id);
    }
}
