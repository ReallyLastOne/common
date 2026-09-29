package org.reallylastone.common.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DeletableRepository<T, ID> extends JpaRepository<T, ID> {
    @Query("select e from #{#entityName} e where id(e) = :id and e.deleted = false")
    Optional<T> findByIdNotDeleted(ID id);

    @Query("select e from #{#entityName} e where id(e) in :ids and e.deleted = false")
    List<T> findAllByIdNotDeleted(Collection<ID> ids);

    @Query("select count(e) > 0 from #{#entityName} e where id(e) = :id and e.deleted = false")
    boolean existsByIdNotDeleted(ID id);

    @Query("select count(e) from #{#entityName} e where id(e) = :id and e.deleted = false")
    long countByIdNotDeleted(ID id);

    @Query("select count(e) from #{#entityName} e where id(e) in :ids")
    long countByIds(Collection<ID> ids);

    @Query("select count(e) from #{#entityName} e where id(e) in :ids and e.deleted = false")
    long countByIdsNotDeleted(Collection<ID> ids);
}
