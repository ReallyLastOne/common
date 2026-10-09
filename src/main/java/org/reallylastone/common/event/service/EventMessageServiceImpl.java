package org.reallylastone.common.event.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.reallylastone.common.event.domain.dto.DataModificationEvent;
import org.reallylastone.common.event.domain.dto.DataModificationEvent.DataModificationAction;
import org.reallylastone.common.event.domain.entity.EventMessage;
import org.reallylastone.common.event.repository.EventMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@ConditionalOnProperty(prefix = "common.event-message", name = "enabled", havingValue = "true")
public class EventMessageServiceImpl implements EventMessageService {

    private final EventMessageRepository eventMessageRepository;
    private final int batchSize;

    public EventMessageServiceImpl(EventMessageRepository repository,
            @Value("${common.event-message.batch-size:100}") int batchSize) {
        this.eventMessageRepository = repository;
        this.batchSize = batchSize;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public EventMessage recordOutbox(String source, String entityType, String entityId,
            DataModificationAction action, Map<String, Object> payload, Instant occurredAt) {
        Instant now = Instant.now();
        return eventMessageRepository.saveAndFlush(
                EventMessage.builder().eventId(UUID.randomUUID()).incoming(false).source(source)
                        .entityType(entityType).entityId(entityId).action(action).payload(payload)
                        .occurredAt(occurredAt == null ? now : occurredAt).createdAt(now).build());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean registerInbox(DataModificationEvent event) {
        return eventMessageRepository.insertInboxIfAbsent(event.getEventId(), event.getSource(),
                event.getEntityType(), event.getEntityId(), event.getAction().name(),
                event.getOccurredAt()) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<EventMessage> lockPendingOutbox() {
        return eventMessageRepository.lockPendingOutbox(batchSize);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void markProcessed(EventMessage message) {
        message.setProcessedAt(Instant.now());
    }
}
