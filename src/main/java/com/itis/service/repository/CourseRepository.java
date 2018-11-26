package com.itis.service.repository;

import com.itis.service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT course FROM Course course JOIN course.tags tag WHERE tag = :tag AND course.number = :number")
    List<Course> findByTagAndNumber(@Param("tag") String tag, @Param("number") Integer number);

    List<Course> findByNumber(Integer number);

}
