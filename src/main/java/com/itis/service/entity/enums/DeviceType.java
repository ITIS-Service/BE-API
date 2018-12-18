package com.itis.service.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.itis.service.exception.ResourceNotFoundException;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DeviceType {

    IOS("iOS"), ANDROID("Android");

    private String type;

    DeviceType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static DeviceType fromValue(String value) {
        return Arrays.stream(DeviceType.values())
                .filter(deviceType -> deviceType.type.equals(value))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Неверный тип устройства"));
    }

    @Override
    @JsonValue
    public String toString() {
        return type;
    }
}
