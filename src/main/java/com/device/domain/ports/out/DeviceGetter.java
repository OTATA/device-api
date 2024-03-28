package com.device.domain.ports.out;

import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;

import java.util.List;
import java.util.Optional;

public interface DeviceGetter {
    public Optional<Device> findByExternalKey(DeviceExternalKey externalKey);
    public List<Device> findByBrand(Brand brand);
    public List<Device> findAll();
}
