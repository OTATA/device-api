package com.device.domain.model;

import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;
import com.device.common.Pair;

import java.time.LocalDateTime;

public class Device {
    private DeviceExternalKey externalKey;
    private String name;
    private Brand brand;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Device(final DeviceExternalKey externalKey, final String name, final Brand brand) {
        this.externalKey = externalKey;
        this.name = name;
        this.brand = brand;
        updatedAt = this.createdAt = LocalDateTime.now();
    }

    public Device(final DeviceExternalKey externalKey, final String name, final Brand brand, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.externalKey = externalKey;
        this.name = name;
        this.brand = brand;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public DeviceExternalKey getExternalKey() {
        return externalKey;
    }

    public String getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public Pair<LocalDateTime, LocalDateTime> getDates() {
        return new Pair(createdAt, updatedAt);
    }
}
