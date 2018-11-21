package com.itis.service.controller;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.dto.CreateCourseDto;
import com.itis.service.dto.TeacherDto;
import com.itis.service.entity.CourseDetails;
import com.itis.service.mapper.CourseDetailsMapper;
import com.itis.service.service.CourseService;
import com.itis.service.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    private final CourseDetailsMapper courseDetailsMapper;

    @Autowired
    public AdminController(CourseService courseService,
                           TeacherService teacherService,
                           CourseDetailsMapper courseDetailsMapper) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.courseDetailsMapper = courseDetailsMapper;
    }

    @PostMapping("/course")
    public CourseDetailsDto createCourse(@RequestBody CreateCourseDto createCourseDto) {
        CourseDetails courseDetails = courseService.createCourse(createCourseDto);
        return courseDetailsMapper.courseDetailsToCourseDetailsDto(courseDetails);
    }

    @PostMapping("/teacher")
    public TeacherDto teacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

}
