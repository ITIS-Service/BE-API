package com.itis.service.service;

import com.itis.service.dto.CreateTeacherDto;
import com.itis.service.dto.TeacherDto;

public interface TeacherService {

    TeacherDto create(CreateTeacherDto createTeacherDto);

}
