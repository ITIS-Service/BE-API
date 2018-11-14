package com.itis.service.controller;
/*
* @author Rustem Khairutdinov 
* @group 11-602
*/

import com.itis.service.dto.*;
import com.itis.service.entity.Student;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "administrators", description = "Operating with admin actions")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Courses SHOWING methods

    @ApiOperation(value = "Get all courses", response = CourseDtoList.class)
    @GetMapping("/courses")
    public CourseDtoList getCourses(){
        CourseDtoList courseDtoList = adminService.getAllCourses();
        return courseDtoList;
    }

    @ApiOperation(value = "Get concrete course details", response = CourseDetailsDto.class)
    @GetMapping("/courses/{course_id}")
    public ResponseEntity<CourseDetailsDto> getCourseDetails(@PathVariable("course_id") Long courseId){
        CourseDetailsDto courseDetailsDto = null;
        try {
            courseDetailsDto = adminService.getCourseDetails(courseId);
        } catch (ResourceNotFoundException ex) {
            // если курс не найден
            return ResponseEntity.
                    notFound()
                    .build();
        } catch (Exception ex){
            // если ещё какая-то ошибка
            return ResponseEntity.
                    notFound()
                    .build();
        }
        return ResponseEntity.ok()
                .body(courseDetailsDto);
    }

    @ApiOperation(value = "Get course's students", response = StudentDtoList.class)
    @GetMapping("/courses/{course_id}/getStudents")
    public ResponseEntity<StudentDtoList> getCourseStudents(@PathVariable("course_id") Long courseId){
        StudentDtoList studentDtoList = null;
        try {
            studentDtoList = adminService.getCourseStudents(courseId);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.
                    notFound()
                    .build();
        } catch (Exception ex) {
            return ResponseEntity.
                    notFound()
                    .build();
        }
        return ResponseEntity.ok()
                .body(studentDtoList);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Courses EDITING methods

    @ApiOperation(value = "Edit concrete course")
    @PutMapping("/courses/{course_id}")
    public ResponseEntity<ResponseDto> putTeacherCourses(@PathVariable("course_id") Long courseId,
                                  @RequestBody CourseDetailsDto courseDetailsDto){
        //TODO Validation
        try {
            adminService.editCourseDetails(courseId, courseDetailsDto);
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.
                    badRequest()
                    .body(new ResponseDto("Course not found", false));
        }
        return ResponseEntity.ok()
                .body(new ResponseDto("Course was edited", true));
    }

    @ApiOperation(value = "Add student to course")
    @PutMapping("/courses/{course_id}/addStudent")
    public ResponseEntity<ResponseDto> putStudentToCourse(@PathVariable("course_id") Long courseId,
                                                         @RequestBody Long studentId){
        //TODO Validation
        try {
            adminService.addStudentToCourse(courseId, studentId);
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.
                    badRequest()
                    .body(new ResponseDto("Course not found", false));
        }
        return ResponseEntity.ok()
                .body(new ResponseDto("Student was added", true));
    }

    @ApiOperation(value = "Add students to course")
    @PutMapping("/courses/{course_id}/addStudents")
    public ResponseEntity<ResponseDto> putStudentsToCourses(@PathVariable("course_id") Long courseId,
                                                          @RequestBody StudentsDto studentsIds){
        //TODO Validation
        try {
            adminService.addStudentsToCourse(courseId, studentsIds);
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.
                    badRequest()
                    .body(new ResponseDto("Course not found", false));
        }
        return ResponseEntity.ok()
                .body(new ResponseDto("Student was added", true));
    }

    //TODO
    @ApiOperation(value = "Get teacher's courses", response = CourseDtoList.class)
    @GetMapping("/teachers_courses")
    public CourseDtoList getTeacherCourses(){
        return new CourseDtoList();
    }

    /* Ненужный метод?
    @ApiOperation(value = "Get all students with their points", response = String.class)
    @GetMapping("/courses/{course_id}/points")
    public ResponseEntity<String> getStudentsPoints(){
        return null;
    }*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Requests SHOWING methods

    @ApiOperation(value = "Get all requests", response = RequestDtoList.class)
    @GetMapping("/requests")
    public ResponseEntity<RequestDtoList> getRequests(){
        RequestDtoList requestDtoList = adminService.getAllRequests();
        return ResponseEntity
                .ok()
                .body(requestDtoList);
    }

    @ApiOperation(value = "Get all requests", response = ResponseDto.class)
    @PostMapping("/requests/accepting")
    public ResponseEntity<ResponseDto> postRequest(@RequestBody RequestAcceptingDto requestAcceptingDto){
        try {
            adminService.receiveRequest(requestAcceptingDto);
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.
                    badRequest()
                    .body(new ResponseDto("Request not found", false));
        }
        return ResponseEntity.ok()
                .body(new ResponseDto("Request was received", true));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Students SHOWING methods

    @ApiOperation(value = "Get all students", response = StudentDtoList.class)
    @GetMapping("/students/getStudents")
    public StudentDtoList getCourses(@RequestBody StudentFindingDto studentFindingDto){
        StudentDtoList studentDtoList = adminService.getAllStudents(studentFindingDto);
        return studentDtoList;
    }
}
