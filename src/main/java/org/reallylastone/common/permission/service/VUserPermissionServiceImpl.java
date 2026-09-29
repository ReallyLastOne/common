package org.reallylastone.common.permission.service;

import java.util.List;

import org.reallylastone.common.permission.repository.VUserPermissionRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class VUserPermissionServiceImpl implements VUserPermissionService {
    private final VUserPermissionRepository vUserPermissionRepository;

    @Override
    public List<String> findPermissionsByUserId(String userId) {
        return vUserPermissionRepository.findByUserId(userId);
    }
}

