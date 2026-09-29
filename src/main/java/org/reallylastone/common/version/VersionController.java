package org.reallylastone.common.version;

import static org.reallylastone.common.utils.Constants.API_PREFIX;

import org.reallylastone.common.version.domain.dto.VersionDto;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Version")
public class VersionController {

    private final BuildProperties buildProperties;

    @Operation(summary = "Get the module name and API version")
    @PermitAll
    @GetMapping(API_PREFIX + "/${app.module}/version")
    public VersionDto getVersion() {
        return new VersionDto(buildProperties.getName(), buildProperties.getVersion());
    }
}
