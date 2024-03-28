package com.device.domain.ports.out;

import com.device.domain.model.Device;

public interface DeviceUpdater {
    public Device update(Device device);
}
