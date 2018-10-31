package com.itis.service.repository;

import com.itis.service.entity.Teacher;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeacherRepository extends UserRepositoryBase<Teacher> {

}
