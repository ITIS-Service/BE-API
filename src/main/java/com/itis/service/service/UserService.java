package com.itis.service.service;

import com.itis.service.dto.ChangePasswordDto;
import com.itis.service.dto.ProfileDto;
import com.itis.service.dto.RegisterDto;

public interface UserService {

    String register(RegisterDto registerDto);
    void updateStudentList();
    void createTestStudents();
    void createAdmin();
    ProfileDto fetchProfile(String email);
    void changePassword(ChangePasswordDto changePasswordDto, String email);

}
