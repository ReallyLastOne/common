package org.reallylastone.common.user.service;

import org.reallylastone.common.service.DeletableMessages;
import org.reallylastone.common.user.repository.VUserRepository;
import org.reallylastone.common.i18n.Messages;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VUserServiceImpl implements VUserService {
    @Getter
    private final VUserRepository repository;
    private final Messages messages;
    @Getter
    private final DeletableMessages deletableMessages;


    public VUserServiceImpl(VUserRepository repository, Messages messages) {
        this.repository = repository;
        this.messages = messages;
        this.deletableMessages = DeletableMessages.of(messages.getMessage("user.not.found"));
    }
}
