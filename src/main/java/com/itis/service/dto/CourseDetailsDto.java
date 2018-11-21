package com.itis.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseDetailsDto {

    private Long id;
    private String name;
    private String description;
    private Integer courseNumber;
    private List<DayTimeDto> dayTimes;
    private String place;
    private TeacherDto teacher;

}
