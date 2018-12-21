package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class UserSettingsDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Boolean courseStatusNotificationEnabled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean pointsNotificationEnabled;

}
