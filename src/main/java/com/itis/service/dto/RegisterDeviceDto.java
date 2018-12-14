package com.itis.service.dto;

import lombok.Data;

@Data
public class RegisterDeviceDto {

    private String name;
    private String os;
    private String token;

}
