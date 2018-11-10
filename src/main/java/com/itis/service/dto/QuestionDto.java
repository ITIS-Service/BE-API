package com.itis.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionDto {

    private long id;
    private String title;
    private List<AnswerDto> answers;

}
