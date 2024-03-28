package com.device.domain.model;

import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceTest {
    @Test void newDevice() {
        UUID externalKey = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Device device = new Device(DeviceExternalKey.of(externalKey), "Device One", Brand.of("Home inc"), now, now);

        assertAll(
                "Validate",
                () -> assertEquals(externalKey, device.getExternalKey().value()),
                () -> assertEquals("Device One", device.getName()),
                () -> assertEquals("Home inc", device.getBrand().name()),
                () -> assertEquals(now, device.getDates().getFirst()),
                () -> assertEquals(now, device.getDates().getSecond())
            );
    }
}