package com.itis.service.mapper;

import com.itis.service.dto.RegisterDeviceDto;
import com.itis.service.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(source = "registerDeviceDto.type", target = "type")
    Device device(RegisterDeviceDto registerDeviceDto);

}
