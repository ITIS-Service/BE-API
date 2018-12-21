package com.itis.service.controller;

import com.itis.service.dto.*;
import com.itis.service.security.SecurityConstants;
import com.itis.service.service.*;
import com.itis.service.validators.StudEmailValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Api(value = "users", description = "Operating with student actions")
public class UserController {

    private final UserService userService;
    private final QuestionService questionService;
    private final CourseService courseService;
    private final PointService pointService;
    private final DeviceService deviceService;

    private final StudEmailValidator studEmailValidator;

    @Autowired
    public UserController(UserService userService,
                          QuestionService questionService,
                          CourseService courseService,
                          PointService pointService,
                          DeviceService deviceService,
                          StudEmailValidator studEmailValidator) {
        this.userService = userService;
        this.questionService = questionService;
        this.courseService = courseService;
        this.pointService = pointService;
        this.deviceService = deviceService;
        this.studEmailValidator = studEmailValidator;
    }

    @ApiOperation(value = "Register new student with new password", response = ProfileDto.class)
    @PostMapping("/registration")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto, Errors errors) {
        studEmailValidator.validate(registerDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDto("Неверный формат e-mail адреса. Используйте почту от личного кабинета КФУ", false));
        }
        String token = userService.register(registerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        return ResponseEntity.ok()
                .headers(headers)
                .body(userService.fetchProfile(registerDto.getEmail()));
    }

    @ApiOperation(value = "Get all questions", response = QuestionDto[].class)
    @GetMapping("/questions")
    public List<QuestionDto> getQuestions() {
        return questionService.fetchAll();
    }

    @ApiOperation(value = "Send selected answers", response = ResponseDto.class)
    @PostMapping("/answers")
    public ResponseDto acceptAnswers(@RequestBody AcceptAnswersDto answersDto,
                                     @ApiIgnore Authentication authentication) {
        questionService.acceptAnswers(answersDto.getAnswers(), authentication.getName());
        return new ResponseDto("Ответы успешно приняты", true);
    }

    @ApiOperation(value = "Get course details")
    @GetMapping("/courses/{courseID}/details")
    public CourseDetailsDto getCourseDetails(@PathVariable long courseID, @ApiIgnore Authentication authentication) {
        return courseService.getDetails(courseID, authentication.getName());
    }

    @ApiOperation(value = "Get suggested and all courses")
    @GetMapping("/courses")
    public ListCoursesDto getCourses(@ApiIgnore Authentication authentication) {
        return courseService.fetch(authentication.getName());
    }

    @ApiOperation(value = "Sign up to selected course")
    @PostMapping("/courses/{courseID}/signUp")
    public CourseDetailsDto signUpCourse(@PathVariable long courseID, @ApiIgnore Authentication authentication) {
        return courseService.signUp(courseID, authentication.getName());
    }

    @ApiOperation(value = "Sign out from selected course")
    @PostMapping("/courses/{courseID}/signOut")
    public CourseDetailsDto signOutCourse(@PathVariable long courseID, @ApiIgnore Authentication authentication) {
        return courseService.signOut(courseID, authentication.getName());
    }

    @ApiOperation(value = "Get points at selected course")
    @GetMapping("/courses/{courseID}/points")
    public UserPointsDto getPoints(@PathVariable long courseID, @ApiIgnore Authentication authentication) {
        return pointService.fetchPoints(courseID, authentication.getName());
    }

    @ApiOperation(value = "Change user password")
    @PostMapping("/profile/password/change")
    public ResponseDto changePassword(
            @RequestBody ChangePasswordDto changePasswordDto,
            @ApiIgnore Authentication authentication) {
        userService.changePassword(changePasswordDto, authentication.getName());
        return new ResponseDto("Пароль успешно изменен", true);
    }

    @ApiOperation(value = "Update user settings")
    @PostMapping("/profile/settings")
    public UserSettingsDto updateUserSettings(
            @RequestBody UserSettingsDto userSettingsDto,
            @ApiIgnore Authentication authentication) {
        return userService.updateUserSettings(userSettingsDto, authentication.getName());
    }

    @ApiOperation(value = "Register device for push notifications")
    @PostMapping("/device/register")
    public ResponseDto registerDevice(
            @RequestBody RegisterDeviceDto registerDeviceDto,
            @ApiIgnore Authentication authentication) {
        deviceService.registerDevice(registerDeviceDto, authentication.getName());
        return new ResponseDto("Устройство успешно зарегестрировано", true);
    }

    @ApiOperation(value = "Unregister device from push notifications")
    @PostMapping("/device/unregister")
    public ResponseDto unregisterDevice(@RequestBody UnregisterDeviceDto unregisterDeviceDto) {
        deviceService.unregisterDevice(unregisterDeviceDto);
        return new ResponseDto("Устройство успешно удалено", true);
    }

}
