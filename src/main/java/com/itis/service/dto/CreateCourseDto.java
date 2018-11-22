package com.itis.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateCourseDto {

    private String name;
    private String description;
    private List<String> tags;
    private Integer courseNumber;
    private List<DayTimeDto> dayTimes;
    private String place;
    private Long teacherID;

}
