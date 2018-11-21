package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.net.URL;

@Data
public class TeacherDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private URL link;

}
