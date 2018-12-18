package com.itis.service.service;

import com.itis.service.dto.*;
import com.itis.service.entity.CourseDetails;

public interface CourseService {

    CourseDetails createCourse(CreateCourseDto createCourseDto);
    CourseDetailsDto getDetails(long courseID, String email);
    ListCoursesDto fetch(String email);
    CourseDetailsDto signUp(long courseID, String email);
    CourseDetailsDto signOut(long courseID, String email);

    void updateStudentsStatus(StudentListDto studentListDto, Long courseID);

}
