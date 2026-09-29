package org.reallylastone.common.permission.utils;

import java.util.List;

import org.reallylastone.common.permission.repository.VUserPermissionRepository;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthSchemaJwtAuthoritiesResolver implements JwtAuthoritiesResolver {

    private final VUserPermissionRepository vUserPermissionRepository;

    @Override
    public List<String> getAuthorities(Jwt jwt) {
        return vUserPermissionRepository.findByUserId(jwt.getSubject()).stream().toList();
    }
}
