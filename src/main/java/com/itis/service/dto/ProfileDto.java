package com.itis.service.dto;

import lombok.Data;

@Data
public class ProfileDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private GroupDto group;
    private boolean isPassedQuiz;

}
