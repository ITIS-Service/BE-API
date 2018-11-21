package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itis.service.entity.enums.Day;
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
    private LocalTime time;

}
