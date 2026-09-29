package org.reallylastone.common.config;

import org.reallylastone.common.permission.domain.entity.VUserPermission;
import org.reallylastone.common.permission.repository.VUserPermissionRepository;
import org.reallylastone.common.permission.service.VUserPermissionService;
import org.reallylastone.common.permission.service.VUserPermissionServiceImpl;
import org.reallylastone.common.permission.utils.AuthSchemaJwtAuthoritiesResolver;
import org.reallylastone.common.permission.utils.JwtAuthoritiesResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnProperty(prefix = "common.authorization", name = "use-auth-view",
        havingValue = "true")
@EnableJpaRepositories(basePackageClasses = VUserPermissionRepository.class)
@EntityScan(basePackageClasses = VUserPermission.class)
public class AuthSchemaConfiguration {

    @Bean
    public JwtAuthoritiesResolver authSchemaJwtRolesResolver(
            VUserPermissionRepository vUserPermissionRepository) {
        return new AuthSchemaJwtAuthoritiesResolver(vUserPermissionRepository);
    }

    @Bean
    public VUserPermissionService vUserRoleService(
            VUserPermissionRepository vUserPermissionRepository) {
        return new VUserPermissionServiceImpl(vUserPermissionRepository);
    }
}
