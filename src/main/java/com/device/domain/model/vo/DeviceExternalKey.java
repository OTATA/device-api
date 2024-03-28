package com.device.domain.model.vo;

import java.util.UUID;

public record DeviceExternalKey(UUID value) {
    public static DeviceExternalKey of(UUID externalKey) {
        return new DeviceExternalKey(externalKey);
    }
}
