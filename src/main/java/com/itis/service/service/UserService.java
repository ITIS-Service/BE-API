package com.itis.service.service;

import com.itis.service.dto.LoginResponseDto;
import com.itis.service.dto.RegisterDto;

public interface UserService {

    String register(RegisterDto registerDto);
    LoginResponseDto loginUser(String email);
    void updateStudentList();

}
