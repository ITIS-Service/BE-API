package com.itis.service.controller;

import com.itis.service.dto.*;
import com.itis.service.entity.Course;
import com.itis.service.mapper.CourseMapper;
import com.itis.service.security.SecurityConstants;
import com.itis.service.service.CourseService;
import com.itis.service.service.QuestionService;
import com.itis.service.service.UserService;
import com.itis.service.validators.StudEmailaValidator;
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

    private final static int SUGGESTED_COURSES_INDEX = 0;
    private final static int ALL_COURSES_INDEX = 1;

    private final UserService userService;
    private final QuestionService questionService;
    private final CourseService courseService;

    private final StudEmailaValidator studEmailaValidator;

    private final CourseMapper courseMapper;

    @Autowired
    public UserController(UserService userService,
                          QuestionService questionService,
                          CourseService courseService,
                          StudEmailaValidator studEmailaValidator) {
        this.userService = userService;
        this.questionService = questionService;
        this.courseService = courseService;
        this.studEmailaValidator = studEmailaValidator;
        this.courseMapper = courseMapper;
    }

    @ApiOperation(value = "Register new student with new password", response = ResponseDto.class)
    @PostMapping("/registration")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegisterDto registerDto, Errors errors) {
        studEmailaValidator.validate(registerDto, errors);
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
                .body(new ResponseDto("Пароль установлен", true));
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

}
