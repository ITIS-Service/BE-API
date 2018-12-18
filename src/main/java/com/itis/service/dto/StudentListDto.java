package com.itis.service.dto;

import com.itis.service.entity.enums.UserCourseStatus;
import lombok.Data;

import java.util.List;

@Data
public class StudentListDto {

    private List<Long> studentIDs;
    private UserCourseStatus status;

}
