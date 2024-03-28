package com.device.domain.exception;

import java.util.Set;

public class NotificationException extends RuntimeException {
    private Set<String> messages;
    public NotificationException(final String message, final Set<String> messages) {
        super(message);
        this.messages = messages;
    }
    public Set<String> getMessages() {
        return messages;
    }
}
