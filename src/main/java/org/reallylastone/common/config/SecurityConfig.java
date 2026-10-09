package org.reallylastone.common.config;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

import org.reallylastone.common.permission.utils.JwtAuthoritiesResolver;
import org.reallylastone.common.permission.utils.KeycloakJwtAuthoritiesResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @ConditionalOnMissingBean(JwtAuthoritiesResolver.class)
    public JwtAuthoritiesResolver keycloakJwtRolesResolver() {
        return new KeycloakJwtAuthoritiesResolver();
    }

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain filterChain(HttpSecurity http,
            JwtAuthoritiesResolver jwtAuthoritiesResolver,
            ObjectProvider<HttpSecurityCustomizer> customizers) {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/actuator/health/**")
                        .permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(
                        resourceServer -> resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(
                                jwtAuthenticationConverter(jwtAuthoritiesResolver))));
        customizers.orderedStream().forEach(customizer -> customizer.customize(http));
        return http.build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter(
            JwtAuthoritiesResolver jwtAuthoritiesResolver) {

        return jwt -> new JwtAuthenticationToken(jwt,
                extractAuthorities(jwtAuthoritiesResolver, jwt),
                Objects.requireNonNull(jwt.getSubject()));
    }

    private Collection<GrantedAuthority> extractAuthorities(
            JwtAuthoritiesResolver jwtAuthoritiesResolver, Jwt jwt) {
        Collection<GrantedAuthority> authorities = new LinkedHashSet<>();
        jwtAuthoritiesResolver.getAuthorities(jwt)
                .forEach(e -> authorities.add(new SimpleGrantedAuthority(e.toLowerCase())));
        return authorities;
    }

}
