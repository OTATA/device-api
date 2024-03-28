package com.device.adapters.rest.dto;

import com.device.domain.model.Device;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeviceResponse(
        UUID externalKey,
        String name,
        String brand,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime udpatedAt
) {

    public static DeviceResponse from(Device device) {
        return new DeviceResponse(
                device.getExternalKey().value(),
                device.getName(),
                device.getBrand().name(),
                device.getDates().getFirst(),
                device.getDates().getSecond()
        );
    }
}
