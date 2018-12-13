package com.itis.service.mapper;

import com.itis.service.dto.RegisterDeviceDto;
import com.itis.service.entity.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    Device device(RegisterDeviceDto registerDeviceDto);

}
