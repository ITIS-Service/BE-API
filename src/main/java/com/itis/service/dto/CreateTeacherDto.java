package com.itis.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.net.URL;

@Data
public class CreateTeacherDto {

    private String firstName;
    private String lastName;
    private String email;

    @ApiModelProperty(dataType = "java.lang.String")
    private URL link;

}
