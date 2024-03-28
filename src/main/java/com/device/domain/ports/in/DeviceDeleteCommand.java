package com.device.domain.ports.in;

import java.util.Optional;
import java.util.UUID;

public record DeviceDeleteCommand(UUID externalKey) {
    public Optional<UUID> getExternalKey() {
        return Optional.of(externalKey);
    }
}
