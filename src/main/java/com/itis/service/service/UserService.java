package com.itis.service.service;

import com.itis.service.dto.*;

public interface UserService {

    String register(RegisterDto registerDto);
    void updateStudentList();
    void createTestStudents();
    void createAdmin();
    ProfileDto fetchProfile(String email);
    void changePassword(ChangePasswordDto changePasswordDto, String email);
    UserSettingsDto updateUserSettings(UserSettingsDto userSettingsDto, String email);
    ProfileDto createStudent(CreateStudentDto createStudentDto);

}
