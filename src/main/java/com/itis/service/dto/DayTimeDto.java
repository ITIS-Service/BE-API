package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itis.service.entity.enums.Day;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayTimeDto {

    private Long id;
    private Day day;

    @JsonFormat(pattern = "HH:mm")
    @ApiModelProperty(dataType = "[Ljava.lang.String;")
    private List<LocalTime> times;

}
