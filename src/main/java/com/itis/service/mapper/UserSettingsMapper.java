package com.itis.service.mapper;

import com.itis.service.dto.UserSettingsDto;
import com.itis.service.entity.UserSettings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSettingsMapper {

    UserSettingsDto userSettingsDto(UserSettings userSettings);

}
