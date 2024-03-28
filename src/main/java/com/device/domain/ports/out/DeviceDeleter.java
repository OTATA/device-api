package com.device.domain.ports.out;

import com.device.domain.model.Device;

public interface DeviceDeleter {
    public void delete(Device device);
}
