package com.itis.service.service.impl;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.dto.CreateCourseDto;
import com.itis.service.entity.*;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.mapper.CourseDetailsMapper;
import com.itis.service.repository.*;
import com.itis.service.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDetailsRepository courseDetailsRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final UserCourseRepository userCourseRepository;

    private final CourseDetailsMapper courseDetailsMapper;

    @Autowired
    public CourseServiceImpl(
            CourseDetailsRepository courseDetailsRepository,
            CourseRepository courseRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            UserCourseRepository userCourseRepository,
            CourseDetailsMapper courseDetailsMapper) {
        this.courseDetailsRepository = courseDetailsRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.userCourseRepository = userCourseRepository;
        this.courseDetailsMapper = courseDetailsMapper;
    }

    public CourseDetails createCourse(CreateCourseDto createCourseDto) {
        Course course = new Course(
                createCourseDto.getName(),
                createCourseDto.getDescription(),
                createCourseDto.getTags(),
                createCourseDto.getCourseNumber()
        );

        Teacher teacher = teacherRepository.findById(createCourseDto.getTeacherID()).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Учитель с ID %d не найден", createCourseDto.getTeacherID()))
        );

        List<DayTime> dayTimes = createCourseDto.getDayTimes().stream()
                .map(dayTime -> new DayTime(dayTime.getDay(), dayTime.getTimes()))
                .collect(Collectors.toList());

        CourseDetails courseDetails = new CourseDetails(
                course,
                dayTimes,
                createCourseDto.getPlace(),
                teacher
        );

        course.setCourseDetails(courseDetails);

        courseRepository.save(course);
        courseDetailsRepository.save(courseDetails);
        courseRepository.flush();
        courseDetailsRepository.flush();

        return courseDetails;
    }

    public CourseDetailsDto getDetails(long courseID, String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException(String.format("Студкет с email %s не найден", email));
        }

        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Курс с ID %d не найден", courseID))
        );

        CourseDetails courseDetails = course.getCourseDetails();

        CourseDetailsDto courseDetailsDto = courseDetailsMapper.courseDetailsToCourseDetailsDto(courseDetails);

        UserCourse userCourse = userCourseRepository.findByUserAndCourseDetails(student, courseDetails);
        if (userCourse != null) {
            courseDetailsDto.setUserCourseStatus(userCourse.getStatus());
        }

        return courseDetailsDto;
    }

}
