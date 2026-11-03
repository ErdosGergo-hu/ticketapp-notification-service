package com.erdos.ticketapp.notificationservice.exception;

public class InvalidNotificationEventException extends RuntimeException {

    public InvalidNotificationEventException(String message) {
        super(message);
    }
}