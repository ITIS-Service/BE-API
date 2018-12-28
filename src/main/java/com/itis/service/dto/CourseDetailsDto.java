package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itis.service.entity.enums.UserCourseStatus;
import lombok.Data;

import java.util.List;

@Data
public class CourseDetailsDto {

    private Long id;
    private CourseDto course;
    private Integer courseNumber;
    private List<DayTimeDto> dayTimes;
    private String place;
    private TeacherDto teacher;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserPointsDto userPoints;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean signUpOpen;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserCourseStatus userCourseStatus;

}
