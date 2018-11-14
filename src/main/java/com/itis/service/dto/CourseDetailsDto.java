package com.itis.service.dto;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CourseDetailsDto {

    private long id;
    private String teacherFullName;
    private String lessonPlace;
    private long teacherId;
    private List<LocalTime> lessonTime;
    private String name;
    private String description;
}
