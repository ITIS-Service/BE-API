package com.itis.service.service;

import com.itis.service.dto.*;
import com.itis.service.exception.ResourceNotFoundException;

public interface AdminService {

    CourseDtoList getAllCourses();

    CourseDetailsDto getCourseDetails(Long courseId) throws ResourceNotFoundException;

    void editCourseDetails(Long id, CourseDetailsDto courseDetailsDto) throws ResourceNotFoundException;

    StudentDtoList getCourseStudents(Long courseId);

    void addStudentToCourse(Long courseId, Long studentId);

    void addStudentsToCourse(Long courseId, StudentsDto studentsIds);

    RequestDtoList getAllRequests();

    void receiveRequest(RequestAcceptingDto requestAcceptingDto);

    //StudentDtoList getAllStudents(StudentFindingDto studentFindingDto);
}
