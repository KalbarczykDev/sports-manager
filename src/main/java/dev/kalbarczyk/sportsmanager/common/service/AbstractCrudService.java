package dev.kalbarczyk.sportsmanager.common.service;

import dev.kalbarczyk.sportsmanager.common.exception.CrudException;
import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.List;

@Slf4j
public abstract class AbstractCrudService<T extends BaseEntity> implements CrudService<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    @Override
    public List<T> findAll(String sortBy, String sortDir) {
        log.info("Fetching all objects of type {}", this.getClass().getSimpleName());
        try {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            return getRepository().findAll(sort);
        } catch (PropertyReferenceException e) {
            throw new CrudException.InvalidSortingArgument(e.getMessage());
        }
    }

    @Override
    public T findById(Long id) {
        log.info("Fetching object of type {}", this.getClass().getSimpleName());
        return getRepository().findById(id).orElseThrow(() -> {
            log.warn("Object {} not found with id {}", this.getClass().getSimpleName(), id);
            return new CrudException.NotFound(this.getClass().getSimpleName());
        });
    }

    @Override
    public T save(T entity) {
        log.info("Saving object of type {}", this.getClass().getSimpleName());
        return getRepository().save(entity);
    }

    @Override
    public T update(Long id, T entity) {
        log.info("Updating object of type {} and id {}", this.getClass().getSimpleName(), id);
        if (!getRepository().existsById(id)) {
            log.warn("Cannot update. Object {} not found with ID: {}", this.getClass().getSimpleName(), id);
            throw new CrudException.NotFound("Competitor not found with id: " + id);
        }
        entity.setId(id);
        return getRepository().save(entity);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting {} with ID: {}", this.getClass().getSimpleName(), id);
        if (!getRepository().existsById(id)) {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        getRepository().deleteById(id);
    }
}
