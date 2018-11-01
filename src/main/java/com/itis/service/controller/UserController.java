package com.itis.service.controller;

import com.itis.service.dto.RegisterDto;
import com.itis.service.dto.ResponseDto;
import com.itis.service.service.UserService;
import com.itis.service.validators.StudEmailaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final StudEmailaValidator studEmailaValidator;

    @Autowired
    public UserController(UserService userService, StudEmailaValidator studEmailaValidator) {
        this.userService = userService;
        this.studEmailaValidator = studEmailaValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegisterDto registerDto, Errors errors) {
        studEmailaValidator.validate(registerDto, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new ResponseDto("Неверный формат e-mail адреса. Используйте почту от личного кабинета КФУ", false),
            HttpStatus.BAD_REQUEST);
        }
        userService.register(registerDto);
        return new ResponseEntity<>(new ResponseDto("Пароль установлен", true), HttpStatus.OK);
    }

    @GetMapping("/initialize")
    public ResponseDto initialize() {
        userService.updateStudentList();
        return new ResponseDto("Студенты обновлены в базе данных", true);
    }

}
