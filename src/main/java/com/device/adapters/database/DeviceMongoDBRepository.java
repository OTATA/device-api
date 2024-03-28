package com.device.adapters.database;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@MongoRepository
public interface DeviceMongoDBRepository extends CrudRepository<DeviceEntity, UUID> {
    public List<DeviceEntity> findAllByBrand(String brand);
}
