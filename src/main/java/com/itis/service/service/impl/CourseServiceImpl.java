package com.itis.service.service.impl;

import com.itis.service.dto.CreateCourseDto;
import com.itis.service.entity.Course;
import com.itis.service.entity.CourseDetails;
import com.itis.service.entity.DayTime;
import com.itis.service.entity.Teacher;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.repository.CourseDetailsRepository;
import com.itis.service.repository.CourseRepository;
import com.itis.service.repository.TeacherRepository;
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

    @Autowired
    public CourseServiceImpl(
            CourseDetailsRepository courseDetailsRepository,
            CourseRepository courseRepository,
            TeacherRepository teacherRepository) {
        this.courseDetailsRepository = courseDetailsRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
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

    public CourseDetails getDetails(long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Курс с ID %d не найден", courseID))
        );

        return course.getCourseDetails();
    }

}
