package com.device.domain.ports.in;

import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;

import java.util.UUID;

public class DeviceCreateCommand extends DeviceCommand {

    private UUID externalKey;
    private String name, brand;

    public DeviceCreateCommand(final String name, final String brand) {
        this.name = name;
        this.brand = brand;

        validate(name, brand);
    }

    public Device toDevice() {
        return new Device(DeviceExternalKey.of(this.externalKey), this.name, Brand.of(this.brand));
    }
}
