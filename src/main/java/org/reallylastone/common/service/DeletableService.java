package org.reallylastone.common.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.reallylastone.common.exception.ResourceNotFoundException;
import org.reallylastone.common.repository.DeletableRepository;

public interface DeletableService<T, ID, R extends DeletableRepository<T, ID>> {
    R getRepository();

    DeletableMessages getDeletableMessages();

    default T getById(ID id) {
        return getRepository().findById(id).orElseThrow(
                () -> new ResourceNotFoundException(getDeletableMessages().notFound()));
    }

    default T getNotDeletedById(ID id) {
        return getRepository().findByIdNotDeleted(id).orElseThrow(
                () -> new ResourceNotFoundException(getDeletableMessages().notFound()));
    }

    default Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    default Optional<T> findNotDeletedById(ID id) {
        return getRepository().findByIdNotDeleted(id);
    }

    default Collection<T> getAllByIds(Collection<ID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        var distinctIds = new HashSet<>(ids);
        var entities = getRepository().findAllByIdNotDeleted(distinctIds);
        if (entities.size() != distinctIds.size()) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
        return entities;
    }

    default Collection<T> findAllByIds(Collection<ID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        var distinctIds = new HashSet<>(ids);
        return getRepository().findAllByIdNotDeleted(distinctIds);
    }

    default boolean exists(ID id) {
        return getRepository().existsById(id);
    }

    default boolean exists(Collection<ID> ids) {
        var deduplicated = new HashSet<>(ids);
        if (deduplicated.isEmpty())
            return true;
        return getRepository().countByIds(deduplicated) == deduplicated.size();
    }

    default boolean existsNotDeleted(ID id) {
        return getRepository().existsByIdNotDeleted(id);
    }

    default boolean existsNotDeleted(Collection<ID> ids) {
        var deduplicated = new HashSet<>(ids);
        if (deduplicated.isEmpty())
            return true;
        return getRepository().countByIdsNotDeleted(deduplicated) == deduplicated.size();
    }

    default void requireExists(ID id) {
        if (!exists(id)) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default void requireExists(Collection<ID> ids) {
        if (!exists(ids)) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default void requireExistsNotDeleted(ID id) {
        if (!existsNotDeleted(id)) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default void requireExistsNotDeleted(Collection<ID> ids) {
        if (!existsNotDeleted(ids)) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }
}
