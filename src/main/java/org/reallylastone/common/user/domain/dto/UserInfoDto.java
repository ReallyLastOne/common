package org.reallylastone.common.user.domain.dto;

import java.util.List;

public record UserInfoDto(String userId, String username, List<String> authorities) {
}
