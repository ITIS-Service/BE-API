package com.itis.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCoursesDto {

    private List<CourseDto> userCourses;
    private List<CourseDto> suggestedCourses;
    private List<CourseDto> allCourses;

}
