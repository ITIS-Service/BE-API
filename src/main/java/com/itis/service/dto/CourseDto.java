package com.itis.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {

    private long id;
    private String name;
    private String description;

}
