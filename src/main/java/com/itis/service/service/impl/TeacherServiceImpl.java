package com.itis.service.service.impl;

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

    public TeacherDto create(TeacherDto teacherDto) {
        Teacher teacher = new Teacher(
                teacherDto.getEmail(),
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getLink()
        );

        teacherRepository.saveAndFlush(teacher);

        teacherDto.setId(teacher.getId());

        return teacherDto;
    }

}
