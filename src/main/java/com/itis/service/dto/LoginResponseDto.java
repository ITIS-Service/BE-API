package com.itis.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Boolean isPassedQuiz;

}
