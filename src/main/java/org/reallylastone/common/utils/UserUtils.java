package org.reallylastone.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.reallylastone.common.user.domain.dto.UserInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : null;
    }

    public static String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken a)
                        ? a.getToken().getClaimAsString("preferred_username")
                        : null;
    }

    public static UserInfoDto getCurrent() {
        var auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return null;

        return new UserInfoDto(auth.getName(),
                auth.getToken().getClaimAsString("preferred_username"),
                UserUtils.getAllAuthorities());
    }

    private static Optional<Authentication> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication == null || !authentication.isAuthenticated()) ? Optional.empty()
                : Optional.of(authentication);
    }

    public static boolean hasAuthority(String authority) {
        return hasAuthority(SecurityContextHolder.getContext().getAuthentication(), authority);
    }

    public static boolean hasAuthority(Authentication authentication, String authority) {
        if (authentication == null)
            return false;

        return authentication.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), authority));
    }

    public static List<String> getAllAuthorities() {
        return getAuthentication().map(e -> e.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).distinct().toList()).orElse(new ArrayList<>());
    }
}
