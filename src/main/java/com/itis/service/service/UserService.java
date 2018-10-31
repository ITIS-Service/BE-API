package com.itis.service.service;

import com.itis.service.dto.RegisterDto;

public interface UserService {

    void register(RegisterDto registerDto);
    void updateStudentList();

}
