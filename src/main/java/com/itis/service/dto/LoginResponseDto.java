package com.itis.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private boolean isPassedQuiz;

}
