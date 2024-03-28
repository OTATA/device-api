package com.device.domain.ports.in;

import com.device.common.notifiable.Notifiable;
import com.device.domain.exception.NotificationException;

class DeviceCommand extends Notifiable {
    void validate(final String name, final String brand) {
        validateName(name);
        validateBrand(brand);
        checkValidate();
    }

    void checkValidate() {
        if (this.hasNotifications()) {
            throw new NotificationException("Invalid device", this.getNotifications());
        }
    }

    void validateName(String name) {
        if (name == null || name.isBlank() || name.length() < 2) {
            this.addNotification("Invalid name");
        }
    }

    void validateBrand(String brand) {
        if (brand == null || brand.isBlank() || brand.length() < 2) {
            this.addNotification("Invalid brand");
        }
    }
}
