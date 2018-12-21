package com.itis.service.controller;

import com.itis.service.dto.*;
import com.itis.service.entity.CourseDetails;
import com.itis.service.mapper.CourseDetailsMapper;
import com.itis.service.service.CourseService;
import com.itis.service.service.TeacherService;
import com.itis.service.service.UserService;
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
    private final UserService userService;

    private final CourseDetailsMapper courseDetailsMapper;

    @Autowired
    public AdminController(CourseService courseService,
                           TeacherService teacherService,
                           UserService userService,
                           CourseDetailsMapper courseDetailsMapper) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.courseDetailsMapper = courseDetailsMapper;
    }

    @ApiOperation(value = "Create new course", response = CourseDetailsDto.class)
    @PostMapping("/course")
    public CourseDetailsDto createCourse(@RequestBody CreateCourseDto createCourseDto) {
        CourseDetails courseDetails = courseService.createCourse(createCourseDto);
        return courseDetailsMapper.courseDetailsToCourseDetailsDto(courseDetails, null);
    }

    @ApiOperation(value = "Create new teacher", response = TeacherDto.class)
    @PostMapping("/teacher")
    public TeacherDto teacher(@RequestBody CreateTeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

    @ApiOperation(value = "Change students status on course")
    @PostMapping("/course/{courseID}/students/status")
    public ResponseDto acceptStudents(@PathVariable Long courseID, @RequestBody StudentListDto studentListDto) {
        courseService.updateStudentsStatus(studentListDto, courseID);
        return new ResponseDto("Статусы успешно обновлены", true);
    }

    @ApiOperation(value = "Create new student")
    @PostMapping("/students")
    public ProfileDto createStudent(@RequestBody CreateStudentDto createStudentDto) {
        return userService.createStudent(createStudentDto);
    }

}
