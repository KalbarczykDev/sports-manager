package dev.kalbarczyk.sportsmanager.common.service;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import org.springframework.data.domain.Page;


public interface CrudService<T extends BaseEntity> {
    Page<T> findAll(final int page, final int size, final String sortBy, final String sortDir);

    T findById(final Long id);

    T save(final T entity);

    T update(final Long id, final T entity);

    void delete(final Long id);
}
