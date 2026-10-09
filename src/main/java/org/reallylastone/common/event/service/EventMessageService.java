package org.reallylastone.common.event.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.reallylastone.common.event.domain.dto.DataModificationEvent;
import org.reallylastone.common.event.domain.dto.DataModificationEvent.DataModificationAction;
import org.reallylastone.common.event.domain.entity.EventMessage;

public interface EventMessageService {

    EventMessage recordOutbox(String source, String entityType, String entityId,
            DataModificationAction action, Map<String, Object> payload, Instant occurredAt);

    boolean registerInbox(DataModificationEvent event);

    List<EventMessage> lockPendingOutbox();

    void markProcessed(EventMessage message);
}
