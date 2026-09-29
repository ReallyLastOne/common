package org.reallylastone.common.user.domain.entity;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "V_USERS", schema = AUTH_SCHEMA)
@Getter
@Setter
@Immutable
public class VUser {
    @Id
    private String userId;

    private String username;

    private String email;

    private boolean deleted;
}
