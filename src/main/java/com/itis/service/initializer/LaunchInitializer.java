package com.itis.service.initializer;

import com.itis.service.service.QuestionService;
import com.itis.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class LaunchInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(LaunchInitializer.class);

    private final Environment environment;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public LaunchInitializer(Environment environment, UserService userService, QuestionService questionService) {
        this.environment = environment;
        this.userService = userService;
        this.questionService = questionService;
    }

    @PostConstruct
    public void init() {
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        LOG.info("Active profile: " + activeProfiles);
        if (activeProfiles.contains("dev")) {
            userService.updateStudentList();
        } else if (activeProfiles.contains("production")) {
            userService.createTestStudents();
        }
        questionService.createQuestionsIfNeeded();
    }

}
