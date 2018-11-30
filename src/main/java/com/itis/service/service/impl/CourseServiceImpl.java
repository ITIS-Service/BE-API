package com.itis.service.service.impl;

import com.itis.service.dto.*;
import com.itis.service.entity.*;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.exception.SignUpCourseException;
import com.itis.service.mapper.CourseDetailsMapper;
import com.itis.service.mapper.CourseMapper;
import com.itis.service.mapper.UserCourseMapper;
import com.itis.service.repository.CourseDetailsRepository;
import com.itis.service.repository.CourseRepository;
import com.itis.service.repository.StudentRepository;
import com.itis.service.repository.TeacherRepository;
import com.itis.service.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private static final int COURSE_LIMIT_COUNT = 2;

    private final CourseDetailsRepository courseDetailsRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    private final CourseDetailsMapper courseDetailsMapper;
    private final CourseMapper courseMapper;
    private final UserCourseMapper userCourseMapper;

    @Autowired
    public CourseServiceImpl(
            CourseDetailsRepository courseDetailsRepository,
            CourseRepository courseRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            CourseDetailsMapper courseDetailsMapper,
            CourseMapper courseMapper,
            UserCourseMapper userCourseMapper) {
        this.courseDetailsRepository = courseDetailsRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseDetailsMapper = courseDetailsMapper;
        this.courseMapper = courseMapper;
        this.userCourseMapper = userCourseMapper;
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

    @Transactional
    public CourseDetailsDto getDetails(long courseID, String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException(String.format("Студент с email %s не найден", email));
        }

        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Курс с ID %d не найден", courseID))
        );

        CourseDetails courseDetails = course.getCourseDetails();

        return courseDetailsMapper.courseDetailsToCourseDetailsDto(courseDetails, student);
    }
  
    public ListCoursesDto fetch(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException("Студент с почтой " + email + " не найден");
        }

        List<Course> suggestedCourses = student.getSuggestedCourses();
        List<Course> allCourses = courseRepository.findByNumber(student.getGroup().getCourse());

        allCourses.removeAll(suggestedCourses);



        return new ListCoursesDto(
                courseMapper.courseListToCourseDtoList(suggestedCourses),
                courseMapper.courseListToCourseDtoList(allCourses)
        );
    }

    @Transactional
    public CourseDetailsDto signUp(long courseID, String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException("Студент с почтой " + email + " не найден");
        }

        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Курс с ID %d не найден", courseID))
        );

        CourseDetails courseDetails = course.getCourseDetails();

        if (courseDetails.getUserCourses().stream().anyMatch(userCourse -> userCourse.getUser().equals(student))) {
            throw new SignUpCourseException();
        }

        if (student.getUserCourses().size() == COURSE_LIMIT_COUNT) {
            throw new SignUpCourseException(true);
        }

        UserCourse userCourse = new UserCourse(student, courseDetails);
        courseDetails.getUserCourses().add(userCourse);

        courseDetailsRepository.saveAndFlush(courseDetails);

        return courseDetailsMapper.courseDetailsToCourseDetailsDto(courseDetails, student);
    }

    @Transactional
    public List<UserCourseDto> fetchMyCourses(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException("Студент с почтой " + email + " не найден");
        }

        List<UserCourse> userCourses = student.getUserCourses();

        return userCourseMapper.userCourseDtoList(userCourses);
    }

}
