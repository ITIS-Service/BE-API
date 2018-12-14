package com.itis.service.service;

import com.itis.service.dto.RegisterDeviceDto;
import com.itis.service.dto.UnregisterDeviceDto;

public interface DeviceService {

    void registerDevice(RegisterDeviceDto registerDeviceDto, String email);
    void unregisterDevice(UnregisterDeviceDto unregisterDeviceDto);

}
