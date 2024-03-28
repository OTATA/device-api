package com.device.adapters.rest.dto;

import jakarta.annotation.Nullable;
import java.util.Set;

public record ErrorResponse(String message, @Nullable Set<String> errors) {}
