package com.device.domain.ports.in;

import com.device.domain.model.Device;

import java.util.UUID;

public interface IDeviceUpdateCommand {
    public Device merge(com.device.domain.model.Device device);
    public UUID getExternalKey();
}
