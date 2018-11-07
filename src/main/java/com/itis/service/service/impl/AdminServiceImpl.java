package com.itis.service.service.impl;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import com.itis.service.dto.CourseDto;
import com.itis.service.dto.CourseDtoList;
import com.itis.service.entity.Course;
import com.itis.service.repository.CourseRepository;
import com.itis.service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final CourseRepository courseRepository;

    @Autowired
    public AdminServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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
}
