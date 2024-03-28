package com.device.domain.ports.in;

import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;

import java.util.UUID;

public class DevicePartialUpdateCommand extends DeviceCommand implements IDeviceUpdateCommand {

    private UUID externalKey;
    private String name, brand;

    public DevicePartialUpdateCommand(final UUID externalKey, final String name, final String brand) {
        this.name = name;
        this.brand = brand;
        this.externalKey = externalKey;
        validate(name, brand);
    }

    @Override
    protected void validate(String name, String brand) {
        if (name != null) {
            validateName(name);
        }
        if (brand != null) {
            validateBrand(brand);
        }

        checkValidate();
    }

    @Override
    public Device merge(Device device) {
        String name = device.getName();
        Brand brand = device.getBrand();

        if (this.name != null) {
            name = this.name;
        }
        if (this.brand != null) {
            brand = Brand.of(this.brand);
        }
        return new Device(device.getExternalKey(), name, brand, device.getDates().getFirst(), device.getDates().getSecond());
    }

    @Override
    public UUID getExternalKey() {
        return externalKey;
    }
}
