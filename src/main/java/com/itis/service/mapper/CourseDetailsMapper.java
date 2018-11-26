package com.itis.service.mapper;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.entity.CourseDetails;
import com.itis.service.entity.UserCourse;
import com.itis.service.entity.enums.UserCourseStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseDetailsMapper {

    @Mappings({
            @Mapping(source = "courseDetails.course.name", target = "name"),
            @Mapping(source = "courseDetails.course.description", target = "description"),
            @Mapping(source = "courseDetails.course.number", target = "courseNumber")
    })
    CourseDetailsDto courseDetailsToCourseDetailsDto(CourseDetails courseDetails);

}
