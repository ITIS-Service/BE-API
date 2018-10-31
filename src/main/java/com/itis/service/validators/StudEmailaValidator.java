package com.itis.service.validators;

import com.itis.service.dto.RegisterDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StudEmailaValidator {

    public void validate(RegisterDto registerDto, Errors errors) {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@stud.kpfu.ru");
        Matcher matcher = pattern.matcher(registerDto.getEmail());
        if (!matcher.find()) {
            errors.reject("email");
        }
    }

}
