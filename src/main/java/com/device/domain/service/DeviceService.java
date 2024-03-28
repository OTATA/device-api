package com.device.domain.service;

import com.device.domain.exception.NotFoundException;
import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;
import com.device.domain.ports.in.DeviceCreateCommand;
import com.device.domain.ports.in.DeviceDeleteCommand;
import com.device.domain.ports.in.IDeviceUpdateCommand;
import com.device.domain.ports.out.DeviceCreator;
import com.device.domain.ports.out.DeviceDeleter;
import com.device.domain.ports.out.DeviceGetter;
import com.device.domain.ports.out.DeviceUpdater;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
public class DeviceService {

    private DeviceCreator creator;
    private DeviceGetter getter;
    private DeviceUpdater updater;
    private DeviceDeleter deleter;

    @Inject
    public DeviceService(DeviceCreator creator, DeviceGetter getter, DeviceUpdater updater, DeviceDeleter deleter) {
        this.creator = creator;
        this.getter = getter;
        this.updater = updater;
        this.deleter = deleter;
    }

    public Device create(DeviceCreateCommand command) {
        return creator.create(command.toDevice());
    }

    public Device update(IDeviceUpdateCommand command) {
        if (command.getExternalKey() == null) {
            throw new NotFoundException("Device not found");
        }

        Device foundedDevice = findByExternalKey(DeviceExternalKey.of(command.getExternalKey()));
        Device device = command.merge(foundedDevice);

        return updater.update(device);
    }

    public void delete(DeviceDeleteCommand command) {
        if (!command.getExternalKey().isPresent()) {
            throw new NotFoundException("Device not found");
        }
        Device device = findByExternalKey(DeviceExternalKey.of(command.getExternalKey().get()));

        deleter.delete(device);
    }

    public Device findByExternalKey(DeviceExternalKey externalKey) {
        Device device = getter.findByExternalKey(externalKey)
                .orElseThrow(() -> new NotFoundException("Device not found"));

        return device;
    }

    public List<Device> findByBrand(Brand brand) {
        return getter.findByBrand(brand);
    }

    public List<Device> findAll() {
        return getter.findAll();
    }
}
