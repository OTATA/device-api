package com.device.domain.ports.in;

import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;

import java.util.UUID;

public class DeviceUpdateCommand extends DeviceCommand implements IDeviceUpdateCommand  {

    private UUID externalKey;
    private String name, brand;

    public DeviceUpdateCommand(final UUID externalKey, final String name, final String brand) {
        this.name = name;
        this.brand = brand;
        this.externalKey = externalKey;
        validate(name, brand);
    }

    @Override
    public Device merge(Device device) {
        return new Device(device.getExternalKey(), this.name, Brand.of(this.brand), device.getDates().getFirst(), device.getDates().getSecond());
    }

    @Override
    public UUID getExternalKey() {
        return externalKey;
    }
}
