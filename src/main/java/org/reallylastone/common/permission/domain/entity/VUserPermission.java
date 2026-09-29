package org.reallylastone.common.permission.domain.entity;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Immutable
@Getter
@Setter
@Table(name = "v_user_permissions", schema = AUTH_SCHEMA)
public class VUserPermission {

    @Id
    private String userPermissionId;

    private String userId;

    private String permission;
}
