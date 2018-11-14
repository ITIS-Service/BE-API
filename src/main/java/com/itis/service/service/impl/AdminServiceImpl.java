package com.itis.service.service.impl;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import com.itis.service.dto.*;
import com.itis.service.entity.*;
import com.itis.service.entity.enums.RequestStatus;
import com.itis.service.entity.enums.RequestType;
import com.itis.service.entity.enums.UserCourseStatus;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.repository.*;
import com.itis.service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final CourseRepository courseRepository;
    private final CourseDetailsRepository courseDetailsRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public AdminServiceImpl(CourseRepository courseRepository, CourseDetailsRepository courseDetailsRepository, StudentRepository studentRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.courseRepository = courseRepository;
        this.courseDetailsRepository = courseDetailsRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public CourseDtoList getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = courses.stream()
                .map(course -> CourseDto.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .description(course.getDescription())
                        .build())
                .collect(Collectors.toList());
        CourseDtoList courseDtoList = new CourseDtoList();
        courseDtoList.setCourseDtoList(courseDtos);
        return courseDtoList;
    }

    @Override
    public CourseDetailsDto getCourseDetails(Long courseId) throws ResourceNotFoundException{
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDetails courseDetails = course.getCourseDetails();
        if(courseDetails == null) throw new ResourceNotFoundException("Child object not found");
        Teacher teacher = courseDetails.getTeacher();
        if(teacher == null) throw new ResourceNotFoundException("Child object not found");
        return CourseDetailsDto.builder()
                .id(course.getId())
                .description(course.getDescription())
                .name(course.getName())
                .lessonPlace(courseDetails.getPlace())
                .lessonTime(courseDetails.getTimes())
                .teacherFullName(teacher.getFirstName() + teacher.getLastName())
                .teacherId(teacher.getId())
                .build();
    }

    @Override
    public void editCourseDetails(Long id, CourseDetailsDto courseDetailsDto) throws ResourceNotFoundException{
        Course course = courseRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDetails courseDetails = course.getCourseDetails();
        if(courseDetails == null) throw new ResourceNotFoundException("Child object not found");
        //TODO мб на все это проверки нужны
        course.setName(courseDetailsDto.getName());
        course.setDescription(courseDetailsDto.getDescription());
        course.getCourseDetails().setCourse(course);
        course.getCourseDetails().setPlace(courseDetailsDto.getLessonPlace());
        course.getCourseDetails().setTimes(courseDetailsDto.getLessonTime());
        //TODO проверить что из них сохраняет оба
        courseDetailsRepository.save(courseDetails);
        courseRepository.save(course);
    }

    @Override
    public StudentDtoList getCourseStudents(Long courseId) {
        Course course = courseRepository.findById(courseId).
                orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        List<StudentDto> studentDtocourses = new ArrayList<>();
                course.getCourseDetails().getUserCourses()
                .stream()
                .map(userCourse -> userCourse.getUser())
                .map(user -> user.getId())
                .map(id -> studentRepository.findById(id).orElse(null))
                .filter(student -> !student.equals(null))
                .map(student -> StudentDto.builder()
                        .studentId(student.getId())
                        .studentCourse(student.getGroup().getName())
                        //.studentCourse(student.getCourse())
                        .studentName(student.getFirstName() + student.getLastName()))
                .collect(Collectors.toList());
        return StudentDtoList.builder()
                .studentDtoList(studentDtocourses)
                .build();
    }

    @Override
    public void addStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).
                orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDetails courseDetails = course.getCourseDetails();
        if(courseDetails == null) throw new ResourceNotFoundException("Child object not found");
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //TODO мб на все это проверки нужны
        UserCourse userCourse = new UserCourse();
        userCourse.setCourseDetails(courseDetails);
        userCourse.setUser(user);
        userCourse.setStatus(UserCourseStatus.MOVED); // Mb UserCourseStatus.ACCEPTED
        user.getUserCourses().add(userCourse);
        courseDetails.getUserCourses().add(userCourse);
        //TODO проверить какой сохраняет все
        userRepository.save(user);
        courseDetailsRepository.save(courseDetails);
        courseRepository.save(course);
    }

    @Override
    public void addStudentsToCourse(Long courseId, StudentsDto studentsIds) {
        Course course = courseRepository.findById(courseId).
                orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDetails courseDetails = course.getCourseDetails();
        if(courseDetails == null) throw new ResourceNotFoundException("Child object not found");
        List<User> users = studentsIds.getStudentIds().stream()
                .map(studentId -> userRepository.findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .collect(Collectors.toList());
        users.forEach(user -> {
            //TODO мб на все это проверки нужны
            UserCourse userCourse = new UserCourse();
            userCourse.setCourseDetails(courseDetails);
            userCourse.setUser(user);
            userCourse.setStatus(UserCourseStatus.MOVED); // Mb UserCourseStatus.ACCEPTED
            user.getUserCourses().add(userCourse);
            courseDetails.getUserCourses().add(userCourse);
            //TODO проверить какой сохраняет все
            userRepository.save(user);
            courseDetailsRepository.save(courseDetails);
            courseRepository.save(course);
        });
    }

    @Override
    public RequestDtoList getAllRequests() {
        List<Request> requests = requestRepository.findAll();
        List<RequestDto> requestDtos = requests.stream()
                .map(request -> RequestDto.builder()
                        .requestId(request.getId())
                        .courseId(request.getCourseDetails().getId())
                        .requestType(request.getType().name())
                        .name(request.getName())
                        .changes(request.getChanges())
                        .creatingDate(request.getDate())
                        .build())
                .collect(Collectors.toList());
        RequestDtoList courseDtoList = RequestDtoList.builder()
                .requestDtos(requestDtos)
                .build();
        return courseDtoList;
    }

    @Override
    public void receiveRequest(RequestAcceptingDto requestAcceptingDto) {
        Request request = requestRepository.findById(requestAcceptingDto.getRequestId()).get();
        Course course = courseRepository.findById(requestAcceptingDto.getCourseId()).get();
        if(requestAcceptingDto.isAccept()) {
            switch (request.getType()) {
                case TITLE_CHANGE:
                    course.setName(request.getChanges());
                    break;
                case DESCRIPTION_CHANGE:
                    course.setDescription(request.getChanges());
                    break;
                case PLACE_CHANGE:
                    course.getCourseDetails().setPlace(request.getChanges());
                    break;
                case TIME_CHANGE:
                    //TODO formatter
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S", Locale.US);
                    LocalDateTime localDateTime = LocalDateTime.parse(request.getChanges(), formatter);
                    course.getCourseDetails().setTimes(Collections.singletonList(localDateTime.toLocalTime()));
                    break;
                case ADD_STUDENT:
                    addStudentToCourse(course.getId(), Long.parseLong(request.getChanges()));
                    break;
                case REMOVE_STUDENT:
                    removeStudent(course.getId(), Long.parseLong(request.getChanges()));
                    break;
                //case MAX_STUDENTS_CHANGE:
                    //course.getCourseDetails()
            }
            request.setStatus(RequestStatus.ACCEPTED);
        }else{
            request.setStatus(RequestStatus.REJECTED);
        }
        requestRepository.save(request);
    }

    /*
    @Override
    public StudentDtoList getAllStudents(StudentFindingDto studentFindingDto) {
        if(studentFindingDto.getFullName() != null){
            String[] fullName = studentFindingDto.getFullName().split(" ");
            List<Student> students = studentRepository.findAllByFirstNameAndLastName(fullName[0], fullName[1]);
            if(studentFindingDto.getGroupName() != null){
                List<Student> respStudents = students.stream()
                        .filter(student -> student.getGroup().getName().equals(studentFindingDto.getGroupName()))
                        .map(student -> StudentDto.builder()
                                .studentId(student.getId())
                                .studentName(student.ge)
                        )

                return StudentDtoList.builder()
                        .studentDtoList()
                        .build()
            }
        }
        studentRepository.findAll().stream()
                .filter(student -> student.getGroup().getName().equals(studentFindingDto.getGroupName()));
    }
    */

    //TODO refactor;
    private void removeStudent(Long courseId, Long studentId){
        Course course = courseRepository.findById(courseId).
                orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDetails courseDetails = course.getCourseDetails();
        if(courseDetails == null) throw new ResourceNotFoundException("Child object not found");
        User user = courseDetails.getUserCourses().stream()
                .map(student -> student.getUser())
                .filter(student -> student.getId() == studentId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Child object not found"));
        UserCourse userCourse = user.getUserCourses().stream()
                .filter(student -> student.getCourseDetails().getId() == courseId)
                .findFirst()
                .get();
        user.getUserCourses().remove(userCourse);
        course.getCourseDetails().getUserCourses().remove(userCourse);
        courseDetails.getUserCourses().add(userCourse);
        //TODO проверить какой сохраняет все
        userRepository.save(user);
        courseDetailsRepository.save(course.getCourseDetails());
        courseRepository.save(course);
    }
}
