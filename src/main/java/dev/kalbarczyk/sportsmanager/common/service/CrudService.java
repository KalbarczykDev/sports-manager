package dev.kalbarczyk.sportsmanager.common.service;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import org.springframework.data.domain.Page;

/**
 * Generic CRUD service interface for entities extending {@link BaseEntity}.
 *
 * @param <T> the type of the entity
 */
public interface CrudService<T extends BaseEntity> {
    /**
     * Finds a paginated and sorted list of entities.
     *
     * @param page    the page number
     * @param size    the page size
     * @param sortBy  the field to sort by
     * @param sortDir the sort direction ("asc" or "desc")
     * @return a page of entities
     */
    Page<T> findAll(final int page, final int size, final String sortBy, final String sortDir);

    /**
     * Finds an entity by its ID.
     *
     * @param id the entity ID
     * @return the entity
     */
    T findById(final Long id);

    /**
     * Saves a new entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(final T entity);

    /**
     * Updates an existing entity by ID.
     *
     * @param id     the entity ID
     * @param entity the updated entity data
     * @return the updated entity
     */
    T update(final Long id, final T entity);

    /**
     * Deletes an entity by ID.
     *
     * @param id the entity ID
     */
    void delete(final Long id);
}
