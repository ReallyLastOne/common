package org.reallylastone.common.permission.utils;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtAuthoritiesResolver {

    List<String> getAuthorities(Jwt jwt);
}
