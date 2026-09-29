package org.reallylastone.common.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.reallylastone.common.exception.ResourceNotFoundException;
import org.reallylastone.common.model.HasUserId;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static final String USER_ID_COLUMN = "user_id";

    public static <T extends HasUserId> T ensureUserAccess(Optional<T> entityOpt) {
        return entityOpt.filter(e -> Objects.equals(e.getUserId(), UserUtils.getCurrentUserId()))
                .orElseThrow(ResourceNotFoundException::new);
    }

    public static <T> Specification<T> withUserIdSpecification() {
        return (root, _, cb) -> cb.equal(root.get(USER_ID_COLUMN), UserUtils.getCurrentUserId());
    }

    public static boolean isDevMode(Environment env) {
        return Arrays.asList(env.getActiveProfiles()).contains("dev");
    }
}
