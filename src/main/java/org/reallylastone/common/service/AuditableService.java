package org.reallylastone.common.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.reallylastone.common.exception.ConflictException;
import org.reallylastone.common.exception.ResourceNotFoundException;
import org.reallylastone.common.model.AuditableEntity;
import org.reallylastone.common.repository.AuditableRepository;

public interface AuditableService<T extends AuditableEntity, ID, R extends AuditableRepository<T, ID>>
        extends DeletableService<T, ID, R> {
    default void markAsDeleted(ID id) {
        if (getRepository().markAsDeleted(id) == 0) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default void restore(ID id) {
        if (getRepository().restore(id) == 0) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default void markAllAsDeleted(Collection<ID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        var distinctIds = new HashSet<>(ids);
        if (getRepository().markAllAsDeleted(distinctIds) != distinctIds.size()) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default void restoreAll(Collection<ID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        var distinctIds = new HashSet<>(ids);
        if (getRepository().restoreAll(distinctIds) != distinctIds.size()) {
            throw new ResourceNotFoundException(getDeletableMessages().notFound());
        }
    }

    default boolean tryMarkAsDeleted(ID id) {
        return getRepository().markAsDeleted(id) > 0;
    }

    default boolean tryRestore(ID id) {
        return getRepository().restore(id) > 0;
    }

    default int tryMarkAllAsDeleted(Collection<ID> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        return getRepository().markAllAsDeleted(new HashSet<>(ids));
    }

    default int tryRestoreAll(Collection<ID> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        return getRepository().restoreAll(new HashSet<>(ids));
    }

    default T getById(ID id, long version) {
        var entity = getById(id);
        if (entity.getVersion() != version) {
            throw new ConflictException();
        }
        return entity;
    }

    default T getNotDeletedById(ID id, long version) {
        var entity = getNotDeletedById(id);
        if (entity.getVersion() != version) {
            throw new ConflictException();
        }
        return entity;
    }

    default Optional<T> findById(ID id, long version) {
        return findById(id).filter(entity -> entity.getVersion() == version);
    }

    default Optional<T> findNotDeletedById(ID id, long version) {
        return findNotDeletedById(id).filter(entity -> entity.getVersion() == version);
    }

    default void requireVersion(ID id, long version) {
        if (getById(id).getVersion() != version) {
            throw new ConflictException();
        }
    }

    default void requireVersionNotDeleted(ID id, long version) {
        if (getNotDeletedById(id).getVersion() != version) {
            throw new ConflictException();
        }
    }
}
