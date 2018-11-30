package com.itis.service.mapper;

import com.itis.service.dto.UserCourseDto;
import com.itis.service.entity.UserCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCourseMapper {

    List<UserCourseDto> userCourseDtoList(List<UserCourse> userCourses);

    @Mappings({
            @Mapping(source = "userCourse.courseDetails.course.id", target = "id"),
            @Mapping(source = "userCourse.courseDetails.course.name", target = "name")
    })
    UserCourseDto userCourseDto(UserCourse userCourse);

}
