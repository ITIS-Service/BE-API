package com.itis.service.controller;
/*
* @author Rustem Khairutdinov 
* @group 11-602
*/

import com.itis.service.dto.CourseDto;
import com.itis.service.dto.CourseDtoList;
import com.itis.service.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Api(value = "administrators", description = "Operating with admin actions")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "Get all courses", response = CourseDtoList.class)
    @GetMapping("/courses")
    public CourseDtoList getCourses(){
        CourseDtoList courseDtoList = adminService.getAllCourses();
        return courseDtoList;
    }
}
