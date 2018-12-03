package com.itis.service.service.impl;

import com.itis.service.dto.CreateTeacherDto;
import com.itis.service.dto.RegisterDto;
import com.itis.service.dto.TeacherDto;
import com.itis.service.entity.Teacher;
import com.itis.service.exception.RegistrationException;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.repository.TeacherRepository;
import com.itis.service.security.JWTProvider;
import com.itis.service.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final JWTProvider jwtProvider;

    @Autowired
    public TeacherServiceImpl(
            PasswordEncoder passwordEncoder,
            TeacherRepository teacherRepository,
            JWTProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
        this.jwtProvider = jwtProvider;
    }

    public String register(RegisterDto registerDto) {
        Teacher teacher = teacherRepository.findByEmail(registerDto.getEmail());
        if (teacher == null) {
            throw new ResourceNotFoundException(String.format("Пользователь с e-mail %s не найден", registerDto.getEmail()));
        }

        if (teacher.getPassword() != null) {
            throw new RegistrationException();
        }

        teacher.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        teacherRepository.saveAndFlush(teacher);

        return jwtProvider.createToken(teacher.getRole().toString(), teacher.getEmail());
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
