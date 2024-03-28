package com.device.domain.model.vo;

public record Brand(String name) {
    public static Brand of(String name) {
        return new Brand(name);
    }
}
