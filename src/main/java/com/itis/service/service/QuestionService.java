package com.itis.service.service;

import com.itis.service.dto.QuestionDto;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<QuestionDto> fetchAll();
    void acceptAnswers(Map<Long, Long> answers, String studentEmail);
    void createQuestionsIfNeeded();

}
