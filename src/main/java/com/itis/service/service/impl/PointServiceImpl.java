package com.itis.service.service.impl;

import com.itis.service.dto.CreatePointDto;
import com.itis.service.dto.PointDto;
import com.itis.service.dto.UserPointsDto;
import com.itis.service.entity.Course;
import com.itis.service.entity.Point;
import com.itis.service.entity.Student;
import com.itis.service.entity.UserCourse;
import com.itis.service.entity.enums.UserCourseStatus;
import com.itis.service.exception.CreatePointException;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.mapper.PointMapper;
import com.itis.service.repository.CourseRepository;
import com.itis.service.repository.PointRepository;
import com.itis.service.repository.StudentRepository;
import com.itis.service.repository.UserCourseRepository;
import com.itis.service.service.NotificationService;
import com.itis.service.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointServiceImpl implements PointService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;
    private final UserCourseRepository userCourseRepository;

    private final PointMapper pointMapper;

    private final NotificationService notificationService;

    @Autowired
    public PointServiceImpl(
            CourseRepository courseRepository,
            StudentRepository studentRepository,
            PointRepository pointRepository,
            UserCourseRepository userCourseRepository,
            PointMapper pointMapper,
            NotificationService notificationService) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.pointRepository = pointRepository;
        this.userCourseRepository = userCourseRepository;
        this.pointMapper = pointMapper;
        this.notificationService = notificationService;
    }

    @Transactional
    public PointDto createPoint(CreatePointDto createPointDto, long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("Курс с ID " + courseID + " не найден")
        );

        Point point = new Point(
                createPointDto.getTitle(),
                createPointDto.getDescription(),
                createPointDto.getCount(),
                course.getCourseDetails()
        );

        List<Student> students = new ArrayList<>();
        for (long studentID : createPointDto.getStudentIDs()) {
            Student student = studentRepository.findById(studentID).orElseThrow(
                    () -> new ResourceNotFoundException("Студент с ID " + studentID + " не найден")
            );

            Optional<UserCourse> userCourseOptional = userCourseRepository.findByUserAndCourseDetails(student, course.getCourseDetails());
            if (userCourseOptional.isPresent()) {
                UserCourse userCourse = userCourseOptional.get();
                if (userCourse.getStatus() == UserCourseStatus.ACCEPTED) {
                    students.add(student);
                    student.getPoints().add(point);
                } else {
                    throw new CreatePointException(String.format("Студент %s %s не принят на курс", student.getLastName(), student.getFirstName()));
                }
            } else {
                throw new CreatePointException(student);
            }
        }

        for (Student student : students) {
            notificationService.sendPointChanged(student, course, point);
        }

        point.getUsers().addAll(students);

        pointRepository.saveAndFlush(point);

        return pointMapper.pointDto(point);
    }

    @Transactional
    public UserPointsDto fetchPoints(long courseID, String email) {
        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("Курс с ID " + courseID + " не найден")
        );

        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException(String.format("Студент с email %s не найден", email));
        }

        List<Point> points = student.getPoints().stream()
                .filter(point -> point.getCourseDetails().getCourse().equals(course))
                .collect(Collectors.toList());

        List<PointDto> pointDtoList = pointMapper.pointDtoList(points);
        int totalSum = 0;

        for (Point point : points) {
            totalSum += point.getCount();
        }

        return new UserPointsDto(totalSum, pointDtoList);
    }

}
