package com.itis.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @ApiModelProperty(dataType = "java.lang.String")
    private URL link;
}
