package org.reallylastone.common.permission.repository;

import java.util.List;

import org.reallylastone.common.permission.domain.entity.VUserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VUserPermissionRepository extends JpaRepository<VUserPermission, String> {

    @Query("select distinct up.permission from #{#entityName} up where up.userId = :userId")
    List<String> findByUserId(@Param("userId") String userId);
}
