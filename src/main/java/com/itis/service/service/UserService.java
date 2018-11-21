package com.itis.service.service;

import com.itis.service.dto.RegisterDto;

public interface UserService {

    String register(RegisterDto registerDto);
    void updateStudentList();
    void createTestStudents();
    void createAdmin();

}
