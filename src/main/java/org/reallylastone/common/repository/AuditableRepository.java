package org.reallylastone.common.repository;

import java.util.Collection;

import org.reallylastone.common.model.AuditableEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface AuditableRepository<T extends AuditableEntity, ID>
        extends DeletableRepository<T, ID> {
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update #{#entityName} e set e.deleted = true, e.modifiedAt = :#{T(java.time.Instant).now()} where id(e) = :id and e.deleted = false")
    int markAsDeleted(ID id);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update #{#entityName} e set e.deleted = false, e.modifiedAt = :#{T(java.time.Instant).now()} where id(e) = :id and e.deleted = true")
    int restore(ID id);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update #{#entityName} e set e.deleted = true, e.modifiedAt = :#{T(java.time.Instant).now()} where id(e) in :ids and e.deleted = false")
    int markAllAsDeleted(Collection<ID> ids);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update #{#entityName} e set e.deleted = false, e.modifiedAt = :#{T(java.time.Instant).now()} where id(e) in :ids and e.deleted = true")
    int restoreAll(Collection<ID> ids);
}
