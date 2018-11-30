package com.itis.service.dto;

import com.itis.service.entity.enums.UserCourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseDto {

    private Long id;
    private String name;
    private UserCourseStatus status;

}
