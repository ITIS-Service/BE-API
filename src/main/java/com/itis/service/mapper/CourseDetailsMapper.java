package com.itis.service.mapper;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.entity.CourseDetails;
import com.itis.service.entity.Student;
import com.itis.service.entity.UserCourse;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CourseDetailsMapper {

    @Mappings({
            @Mapping(source = "courseDetails.course.name", target = "name"),
            @Mapping(source = "courseDetails.course.description", target = "description"),
            @Mapping(source = "courseDetails.course.number", target = "courseNumber"),
    })
    CourseDetailsDto courseDetailsToCourseDetailsDto(CourseDetails courseDetails, @Context Student student);

    @AfterMapping
    default void afterMapping(CourseDetails courseDetails,
                              @MappingTarget CourseDetailsDto courseDetailsDto,
                              @Context Student student) {
        if (student == null) {
            return;
        }

        Optional<UserCourse> userCourseOptional = courseDetails.getUserCourses().stream()
                .filter(userCourse -> userCourse.getUser().equals(student) && userCourse.getCourseDetails().equals(courseDetails))
                .findFirst();

        userCourseOptional.ifPresent(userCourse -> courseDetailsDto.setUserCourseStatus(userCourse.getStatus()));

        courseDetailsDto.setSignUpOpen(student.getUserCourses().size() != 2);
    }
}
