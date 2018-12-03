package com.itis.service.repository;

import com.itis.service.entity.CourseDetails;
import com.itis.service.entity.User;
import com.itis.service.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourse.UserCourseId> {

    Optional<UserCourse> findByUserAndCourseDetails(User user, CourseDetails courseDetails);

}
