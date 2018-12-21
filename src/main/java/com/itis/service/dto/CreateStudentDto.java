package com.itis.service.dto;

import lombok.Data;

@Data
public class CreateStudentDto {

    private String email;
    private String firstName;
    private String lastName;
    private Long groupID;

}
