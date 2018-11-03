package com.itis.service.repository;

import com.itis.service.entity.Student;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StudentRepository extends UserRepositoryBase<Student> {

}
