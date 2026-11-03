package com.erdos.ticketapp.notificationservice.kafka;

import com.erdos.ticketapp.notificationservice.exception.InvalidNotificationEventException;
import com.erdos.ticketapp.notificationservice.model.NotificationEvent;
import com.erdos.ticketapp.notificationservice.repository.ProcessedMessageRepository;
import com.erdos.ticketapp.notificationservice.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final ProcessedMessageRepository processedMessages;
    private final NotificationSender notificationSender;

    @Transactional
    @KafkaListener(topics = "${app.kafka.notification-topic}")
    public void consume(NotificationEvent event) {
        validate(event);

        int inserted =
                processedMessages.insertIfAbsent(event.messageId());

        if (inserted == 0) {
            log.info(
                    "Duplicate Kafka message ignored: {}",
                    event.messageId()
            );
            return;
        }

        notificationSender.send(event);

        log.info(
                "Notification processed: messageId={}, eventId={}, type={}",
                event.messageId(),
                event.eventId(),
                event.type()
        );
    }

    private void validate(NotificationEvent event) {
        if (event == null) {
            throw new InvalidNotificationEventException(
                    "Notification event must not be null"
            );
        }

        if (event.messageId() == null) {
            throw new InvalidNotificationEventException(
                    "Notification messageId is required"
            );
        }

        if (event.eventId() == null) {
            throw new InvalidNotificationEventException(
                    "Notification eventId is required"
            );
        }

        if (event.type() == null || event.type().isBlank()) {
            throw new InvalidNotificationEventException(
                    "Notification type is required"
            );
        }

        if (event.message() == null || event.message().isBlank()) {
            throw new InvalidNotificationEventException(
                    "Notification message is required"
            );
        }
    }
}