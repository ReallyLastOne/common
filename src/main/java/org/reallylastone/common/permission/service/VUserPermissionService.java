package org.reallylastone.common.permission.service;

import java.util.List;

public interface VUserPermissionService {

    List<String> findPermissionsByUserId(String userId);
}
