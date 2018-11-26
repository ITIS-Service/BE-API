package com.itis.service.service.impl;

import com.itis.service.dto.CreateCourseDto;
import com.itis.service.entity.*;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.repository.CourseDetailsRepository;
import com.itis.service.repository.CourseRepository;
import com.itis.service.repository.StudentRepository;
import com.itis.service.repository.TeacherRepository;
import com.itis.service.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDetailsRepository courseDetailsRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl(
            CourseDetailsRepository courseDetailsRepository,
            CourseRepository courseRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository) {
        this.courseDetailsRepository = courseDetailsRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
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
                .map(dayTime -> new DayTime(dayTime.getDay(), dayTime.getTime()))
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

    public List<List<Course>> fetch(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException("Студент с почтой " + email + " не найден");
        }

        List<Course> suggestedCourses = student.getSuggestedCourses();
        List<Course> allCourses = courseRepository.findByNumber(student.getGroup().getCourse());

        allCourses.removeAll(suggestedCourses);

        return Arrays.asList(suggestedCourses, allCourses);
    }

}
