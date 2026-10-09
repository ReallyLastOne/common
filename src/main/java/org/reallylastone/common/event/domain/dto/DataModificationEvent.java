package org.reallylastone.common.event.domain.dto;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataModificationEvent {

    private UUID eventId;
    private String source;
    private String entityType;
    private String entityId;
    private DataModificationAction action;
    private Instant occurredAt;
    private Map<String, Object> payload;

    public enum DataModificationAction {

        CREATED, UPDATED, DELETED
    }
}