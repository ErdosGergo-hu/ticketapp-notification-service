package com.erdos.ticketapp.notificationservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationEventConsumer {

    @KafkaListener(topics = "${app.kafka.notification-topic}")
    public void consume(NotificationEvent notificationEvent) {
        log.info("Notification received: eventId={}, type={}, message={}, occurredAt={}",
                notificationEvent.eventId(),
                notificationEvent.type(),
                notificationEvent.message(),
                notificationEvent.occurredAt());
    }
}
