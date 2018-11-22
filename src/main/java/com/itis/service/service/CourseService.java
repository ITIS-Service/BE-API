package com.itis.service.service;

import com.itis.service.dto.CreateCourseDto;
import com.itis.service.entity.CourseDetails;

public interface CourseService {

    CourseDetails createCourse(CreateCourseDto createCourseDto);

}
