package com.itis.service.service;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.dto.CreateCourseDto;
import com.itis.service.dto.ListCoursesDto;
import com.itis.service.entity.CourseDetails;

public interface CourseService {

    CourseDetails createCourse(CreateCourseDto createCourseDto);
    CourseDetailsDto getDetails(long courseID, String email);
    ListCoursesDto fetch(String email);

}
