package com.itis.service.service;

import com.itis.service.dto.CreateTeacherDto;
import com.itis.service.dto.RegisterDto;
import com.itis.service.dto.TeacherDto;

public interface TeacherService {

    String register(RegisterDto registerDto);
    TeacherDto create(CreateTeacherDto createTeacherDto);

}
