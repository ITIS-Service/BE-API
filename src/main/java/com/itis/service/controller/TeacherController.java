package com.itis.service.controller;

import com.itis.service.dto.CreatePointDto;
import com.itis.service.dto.PointDto;
import com.itis.service.dto.RegisterDto;
import com.itis.service.dto.ResponseDto;
import com.itis.service.security.SecurityConstants;
import com.itis.service.service.PointService;
import com.itis.service.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teacher")
@Api(value = "teacher", description = "Teacher operations")
public class TeacherController {

    private final PointService pointService;
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(PointService pointService, TeacherService teacherService) {
        this.pointService = pointService;
        this.teacherService = teacherService;
    }

    @ApiOperation(value = "Register new teacher with new password", response = ResponseDto.class)
    @PostMapping("/registration")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegisterDto registerDto) {
        String token = teacherService.register(registerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseDto("Пароль установлен", true));
    }

    @ApiOperation(value = "Create new point to students on course")
    @PostMapping("/courses/{courseID}/mark")
    public PointDto createPoint(@PathVariable long courseID, @RequestBody CreatePointDto createPointDto) {
        return pointService.createPoint(createPointDto, courseID);
    }

}
