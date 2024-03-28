package com.device.adapters.database;

import com.device.domain.model.Device;
import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;
import com.device.common.Pair;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedEntity
public record DeviceEntity(
        @BsonId @Id UUID externalKey,
        String name,
        String brand,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DeviceEntity from(Device device) {
        Pair<LocalDateTime, LocalDateTime> dates = device.getDates();

        UUID externalKey = UUID.randomUUID();

        if (device.getExternalKey().value() != null) {
            externalKey = device.getExternalKey().value();
        }

        return new DeviceEntity(externalKey, device.getName(), device.getBrand().name(), dates.getFirst(), dates.getSecond());
    }

    public static DeviceEntity from(Device device, LocalDateTime updatedAt) {
        return new DeviceEntity(device.getExternalKey().value(), device.getName(), device.getBrand().name(), device.getDates().getFirst(), updatedAt);
    }

    public Device toDomain() {
        return new Device(DeviceExternalKey.of(this.externalKey), this.name, Brand.of(this.brand), createdAt, updatedAt);
    }
}
