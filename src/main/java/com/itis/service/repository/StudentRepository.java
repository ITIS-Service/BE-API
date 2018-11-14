package com.itis.service.repository;

import com.itis.service.entity.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudentRepository extends UserRepositoryBase<Student> {

    List<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
}
