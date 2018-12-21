package com.itis.service.validators;

import com.itis.service.dto.RegisterDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StudEmailValidator {

    public static boolean validate(String email) {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@stud.kpfu.ru");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public void validate(RegisterDto registerDto, Errors errors) {
        if (!validate(registerDto.getEmail())) {
            errors.reject("email");
        }
    }

}
