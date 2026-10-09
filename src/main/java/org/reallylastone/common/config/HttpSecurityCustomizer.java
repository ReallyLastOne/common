package org.reallylastone.common.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@FunctionalInterface
public interface HttpSecurityCustomizer {
    void customize(HttpSecurity http);
}
