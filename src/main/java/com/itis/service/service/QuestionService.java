package com.itis.service.service;

import com.itis.service.dto.QuestionDto;

import java.util.List;

public interface QuestionService {

    List<QuestionDto> fetchAll();
    void createQuestionsIfNeeded();

}
