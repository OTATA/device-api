package com.device.common.notifiable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Notifiable {
    private final HashSet<String> notifications;

    public Notifiable() {
        this.notifications = new HashSet<>();
    }

    public void addNotification(String message) {
        this.notifications.add(message);
    }

    public void addNotifiable(Notifiable notifiable) {
        this.notifications.addAll(notifiable.getNotifications());
    }

    public boolean hasNotifications() {
        return !this.notifications.isEmpty();
    }

    public boolean isEmptyNotifications() {
        return this.notifications.isEmpty();
    }

    public Set<String> getNotifications() {
        return Collections.unmodifiableSet(this.notifications);
    }

    @Override
    public String toString() {
        return "Notifiable{notifications=" + this.notifications + '}';
    }
}