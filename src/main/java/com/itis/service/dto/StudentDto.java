package com.itis.service.dto;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {

    private String studentName;
    private long studentId;
    private String studentGroup;
    private String studentCourse;
}
