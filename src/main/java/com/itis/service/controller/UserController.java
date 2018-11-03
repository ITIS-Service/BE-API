package com.itis.service.controller;

import com.itis.service.dto.RegisterDto;
import com.itis.service.dto.ResponseDto;
import com.itis.service.security.SecurityConstants;
import com.itis.service.service.UserService;
import com.itis.service.validators.StudEmailaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/initialize")
    public ResponseDto initialize() {
        userService.updateStudentList();
        return new ResponseDto("Студенты обновлены в базе данных", true);
    }

}
