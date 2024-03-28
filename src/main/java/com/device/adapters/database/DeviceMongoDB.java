package com.device.adapters.database;

import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;
import com.device.domain.ports.out.DeviceCreator;
import com.device.domain.ports.out.DeviceDeleter;
import com.device.domain.ports.out.DeviceGetter;
import com.device.domain.ports.out.DeviceUpdater;
import jakarta.inject.Singleton;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
final public class DeviceMongoDB implements DeviceCreator, DeviceUpdater, DeviceDeleter, DeviceGetter {

    private final DeviceMongoDBRepository repository;

    public DeviceMongoDB(DeviceMongoDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public Device create(Device device) {
        Device deviceCreated = repository.save(
                DeviceEntity.from(device)
        ).toDomain();

        LoggerFactory.getLogger(this.getClass().getName()).info("Device created {}", deviceCreated.getExternalKey());

        return deviceCreated;
    }

    @Override
    public void delete(Device device) {
        repository.delete(DeviceEntity.from(device));
    }

    @Override
    public Optional<Device> findByExternalKey(DeviceExternalKey externalKey) {
        final Optional<DeviceEntity> entity = repository.findById(externalKey.value());
        if(!entity.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(entity.get().toDomain());
    }

    @Override
    public List<Device> findByBrand(Brand brand) {
        return repository.findAllByBrand(brand.name()).stream().map(this::entityToDomain).toList();
    }

    @Override
    public List<Device> findAll() {
        return repository.findAll().stream().map(this::entityToDomain).toList();
    }

    @Override
    public Device update(Device device) {
        return repository.update(DeviceEntity.from(device, LocalDateTime.now())).toDomain();
    }

    private Device entityToDomain(DeviceEntity entity) {
        return entity.toDomain();
    }
}
