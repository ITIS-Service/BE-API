package com.itis.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterDto {

    @ApiModelProperty(notes = "Email of student from kpfu.ru", required = true)
    private String email;

    @ApiModelProperty(notes = "New created password", required = true)
    private String password;

}
