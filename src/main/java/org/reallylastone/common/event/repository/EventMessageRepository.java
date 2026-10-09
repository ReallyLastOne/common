package org.reallylastone.common.event.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.reallylastone.common.event.domain.entity.EventMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventMessageRepository extends JpaRepository<EventMessage, Long> {

    @Query(value = "SELECT * FROM event_messages WHERE incoming = false AND processed_at IS NULL ORDER BY event_message_id LIMIT :limit FOR UPDATE SKIP LOCKED",
            nativeQuery = true)
    List<EventMessage> lockPendingOutbox(@Param("limit") int limit);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "INSERT INTO event_messages (event_id, incoming, source, entity_type, entity_id, action, occurred_at, processed_at) VALUES (:eventId, true, :source, :entityType, :entityId, :action, :occurredAt, now()) ON CONFLICT (incoming, event_id) DO NOTHING",
            nativeQuery = true)
    int insertInboxIfAbsent(@Param("eventId") UUID eventId, @Param("source") String source,
            @Param("entityType") String entityType, @Param("entityId") String entityId,
            @Param("action") String action, @Param("occurredAt") Instant occurredAt);
}
