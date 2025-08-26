package dev.kalbarczyk.sportsmanager.common.service;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;

import java.util.List;

public interface CrudService<T extends BaseEntity> {
    List<T> findAll(final String sortBy, final String sortDir);

    T findById(final Long id);

    T save(final T entity);

    T update(final Long id, final T entity);

    void delete(final Long id);
}
