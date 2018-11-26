package com.itis.service.service;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.dto.CreateCourseDto;
import com.itis.service.entity.Course;
import com.itis.service.entity.CourseDetails;

import java.util.List;

public interface CourseService {

    CourseDetails createCourse(CreateCourseDto createCourseDto);
    CourseDetailsDto getDetails(long courseID, String email);
    List<List<Course>> fetch(String email);

}
