package com.itis.service.mapper;

import com.itis.service.dto.CourseDetailsDto;
import com.itis.service.dto.PointDto;
import com.itis.service.dto.UserPointsDto;
import com.itis.service.entity.CourseDetails;
import com.itis.service.entity.Point;
import com.itis.service.entity.Student;
import com.itis.service.entity.UserCourse;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseDetailsMapper {

    @Mappings({
            @Mapping(source = "courseDetails.course.number", target = "courseNumber"),
            @Mapping(target = "userPoints", ignore = true)
    })
    CourseDetailsDto courseDetailsToCourseDetailsDto(CourseDetails courseDetails, @Context Student student);

    @AfterMapping
    default void afterMapping(CourseDetails courseDetails,
                              @MappingTarget CourseDetailsDto courseDetailsDto,
                              @Context Student student) {
        if (student == null) {
            return;
        }

        List<Point> points = courseDetails.getPoints().stream().filter(point -> point.getUsers().contains(student)).collect(Collectors.toList());
        if (points.isEmpty()) {
            courseDetailsDto.setUserPoints(null);
        } else {
            List<PointDto> pointsDto = PointMapper.INSTANCE.pointDtoList(points);
            int total = points.stream().mapToInt(Point::getCount).sum();
            courseDetailsDto.setUserPoints(new UserPointsDto(total, pointsDto));
        }

        Optional<UserCourse> userCourseOptional = courseDetails.getUserCourses().stream()
                .filter(userCourse -> userCourse.getUser().equals(student) && userCourse.getCourseDetails().equals(courseDetails))
                .findFirst();

        userCourseOptional.ifPresent(userCourse -> courseDetailsDto.setUserCourseStatus(userCourse.getStatus()));

        courseDetailsDto.setSignUpOpen(student.getUserCourses().size() != 2);
    }
}
