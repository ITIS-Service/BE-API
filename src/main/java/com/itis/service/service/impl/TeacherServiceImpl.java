package com.itis.service.service.impl;

import com.itis.service.dto.CreateTeacherDto;
import com.itis.service.dto.TeacherDto;
import com.itis.service.entity.Teacher;
import com.itis.service.repository.TeacherRepository;
import com.itis.service.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherDto create(CreateTeacherDto createTeacherDto) {
        Teacher teacher = new Teacher(
                createTeacherDto.getEmail(),
                createTeacherDto.getFirstName(),
                createTeacherDto.getLastName(),
                createTeacherDto.getLink()
        );

        teacherRepository.saveAndFlush(teacher);

        return new TeacherDto(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getLink()
        );
    }

}
