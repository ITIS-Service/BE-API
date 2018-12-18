package com.itis.service.dto;

import com.itis.service.entity.enums.DeviceType;
import lombok.Data;

@Data
public class RegisterDeviceDto {

    private String name;
    private String os;
    private String token;
    private DeviceType type;

}
