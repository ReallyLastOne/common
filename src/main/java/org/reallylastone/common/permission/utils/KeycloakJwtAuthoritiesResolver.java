package org.reallylastone.common.permission.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakJwtAuthoritiesResolver implements JwtAuthoritiesResolver {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public List<String> getAuthorities(Jwt jwt) {
        return realmRoles(jwt);
    }

    @SuppressWarnings("unchecked")
    private static List<String> realmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_CLAIM);
        if (realmAccess == null || !(realmAccess.get(ROLES_CLAIM) instanceof Iterable<?> roles)) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        for (Object role : roles) {
            result.add(String.valueOf(role));
        }
        return result.stream().distinct().toList();
    }

}
