package com.itis.service.mapper;

import com.itis.service.dto.CourseDto;
import com.itis.service.entity.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    List<CourseDto> courseListToCourseDtoList(List<Course> course);

}
