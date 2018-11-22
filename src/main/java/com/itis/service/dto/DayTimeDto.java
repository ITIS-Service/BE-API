package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itis.service.entity.enums.Day;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayTimeDto {

    private Day day;

    @JsonFormat(pattern = "HH:mm")
    @ApiModelProperty(dataType = "java.lang.String")
    private LocalTime time;

}
