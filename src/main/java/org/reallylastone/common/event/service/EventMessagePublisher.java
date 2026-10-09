package org.reallylastone.common.event.service;

import java.util.List;

import org.reallylastone.common.event.domain.dto.DataModificationEvent;
import org.reallylastone.common.event.domain.entity.EventMessage;
import org.reallylastone.leader_election_lib.LeadershipService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@ConditionalOnProperty(prefix = "common.event-message", name = "enabled", havingValue = "true")
@ConditionalOnProperty(prefix = "common.leadership", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class EventMessagePublisher {
    public static final String DATA_MODIFICATION_EXCHANGE = "data-modification";
    private final EventMessageService eventMessageService;
    private final RabbitTemplate rabbitTemplate;
    private final LeadershipService leadershipService;

    @Transactional
    @Scheduled(fixedDelayString = "${common.event-message.relay-delay-ms:1000}")
    public void publishPending() {
        if (!leadershipService.amILeader())
            return;

        List<EventMessage> pending = eventMessageService.lockPendingOutbox();
        for (EventMessage message : pending) {
            DataModificationEvent event = DataModificationEvent.builder()
                    .eventId(message.getEventId()).source(message.getSource())
                    .entityType(message.getEntityType()).entityId(message.getEntityId())
                    .action(message.getAction()).occurredAt(message.getOccurredAt())
                    .payload(message.getPayload()).build();

            String routingKey = message.getSource() + "." + message.getEntityType() + "."
                    + message.getAction().name().toLowerCase();

            rabbitTemplate.convertAndSend(DATA_MODIFICATION_EXCHANGE, routingKey, event);
            eventMessageService.markProcessed(message);
        }
    }
}
