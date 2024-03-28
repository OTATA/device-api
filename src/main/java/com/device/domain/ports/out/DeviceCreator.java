package com.device.domain.ports.out;

import com.device.domain.model.Device;

public interface DeviceCreator {
    public Device create(Device device);
}
