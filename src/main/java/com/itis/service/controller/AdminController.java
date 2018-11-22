package com.itis.service.controller;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.dto.CreateCourseDto;
import com.itis.service.dto.CreateTeacherDto;
import com.itis.service.dto.TeacherDto;
import com.itis.service.entity.CourseDetails;
import com.itis.service.mapper.CourseDetailsMapper;
import com.itis.service.service.CourseService;
import com.itis.service.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "admin", description = "Admin operations")
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

    @ApiOperation(value = "Create new course", response = CourseDetailsDto.class)
    @PostMapping("/course")
    public CourseDetailsDto createCourse(@RequestBody CreateCourseDto createCourseDto) {
        CourseDetails courseDetails = courseService.createCourse(createCourseDto);
        return courseDetailsMapper.courseDetailsToCourseDetailsDto(courseDetails);
    }

    @ApiOperation(value = "Create new teacher", response = TeacherDto.class)
    @PostMapping("/teacher")
    public TeacherDto teacher(@RequestBody CreateTeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

}
