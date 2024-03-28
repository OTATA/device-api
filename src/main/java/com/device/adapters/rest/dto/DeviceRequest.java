package com.device.adapters.rest.dto;

import com.device.domain.ports.in.DeviceCreateCommand;
import com.device.domain.ports.in.DevicePartialUpdateCommand;
import com.device.domain.ports.in.DeviceUpdateCommand;
import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

@Introspected
public record DeviceRequest(String name, String brand) {

    public DeviceUpdateCommand toUpdateCommand(UUID externalKey) {
        DeviceUpdateCommand command = new DeviceUpdateCommand(externalKey, name, brand);
        return command;
    }

    public DeviceCreateCommand toCreateCommand() {
        DeviceCreateCommand command = new DeviceCreateCommand(name, brand);
        return command;
    }

    public DevicePartialUpdateCommand toPartialUpdateCommand(UUID externalKey) {
        DevicePartialUpdateCommand command = new DevicePartialUpdateCommand(externalKey, name, brand);
        return command;
    }
}
