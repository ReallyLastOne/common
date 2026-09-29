package org.reallylastone.common.user.service;

import org.reallylastone.common.service.DeletableService;
import org.reallylastone.common.user.domain.entity.VUser;
import org.reallylastone.common.user.repository.VUserRepository;

public interface VUserService extends DeletableService<VUser, String, VUserRepository> {
}
