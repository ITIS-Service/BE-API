package com.itis.service.service;

import com.itis.service.entity.Course;
import com.itis.service.entity.Point;
import com.itis.service.entity.Student;
import com.itis.service.entity.enums.UserCourseStatus;

public interface NotificationService {

    void sendCourseStatusChanged(Student student, Course course, UserCourseStatus status);
    void sendPointChanged(Student student, Course course, Point point);

}
