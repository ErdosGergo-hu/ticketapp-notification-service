package com.erdos.ticketapp.notificationservice.model;

import java.time.Instant;
import java.util.UUID;

public record NotificationEvent(
        UUID messageId,
        UUID eventId,
        String type,
        String message,
        Instant occurredAt
) {
}