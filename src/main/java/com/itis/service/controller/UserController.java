package com.itis.service.controller;

import com.itis.service.dto.RegisterDto;
import com.itis.service.dto.ResponseDto;
import com.itis.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return new ResponseDto("Пароль установлен", true);
    }

    @GetMapping("/users/initialize")
    public ResponseDto initialize() {
        userService.updateStudentList();
        return new ResponseDto("Студенты обновлены в базе данных", true);
    }

}
