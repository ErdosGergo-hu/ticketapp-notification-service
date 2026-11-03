package com.erdos.ticketapp.notificationservice.service;

import com.erdos.ticketapp.notificationservice.model.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationSender {

    public void send(NotificationEvent event) {
        log.info(
                "Sending notification: messageId={}, eventId={}, type={}, message={}",
                event.messageId(),
                event.eventId(),
                event.type(),
                event.message()
        );
    }
}
