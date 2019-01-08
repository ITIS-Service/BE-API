package com.itis.service.service.impl;

import com.itis.service.dto.AnswerDto;
import com.itis.service.dto.QuestionDto;
import com.itis.service.entity.*;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.repository.*;
import com.itis.service.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<QuestionDto> fetchAll() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> {
            List<AnswerDto> answersDto = question
                    .getAnswers()
                    .stream()
                    .map(answer -> new AnswerDto(answer.getId(), answer.getTitle())).collect(Collectors.toList());

            return new QuestionDto(question.getId(), question.getTitle(), answersDto);
        }).collect(Collectors.toList());
    }

    public void acceptAnswers(Map<Long, Long> answers, String studentEmail) {
        Student student = studentRepository.findByEmail(studentEmail);
        if (student == null) {
            throw new ResourceNotFoundException("Студент с почтой " + studentEmail + " не найден");
        }

        Integer userCourseNumber = student.getGroup().getCourse();

        List<Course> suggestedCourses = new ArrayList<>();

        for (Map.Entry<Long, Long> mapAnswers : answers.entrySet()) {
            Answer answer = answerRepository.findById(mapAnswers.getValue()).orElseThrow(
                    () -> new ResourceNotFoundException("Ответ с ID = " + mapAnswers.getValue() + " не найден")
            );

            for (String tag : answer.getTags()) {
                List<Course> courses = courseRepository.findByTagAndNumber(tag, userCourseNumber);
                suggestedCourses.addAll(courses);
            }
        }

        List<Course> distinctSuggestedCourses = suggestedCourses.stream().distinct().collect(Collectors.toList());

        student.setPassedQuiz(true);
        student.setSuggestedCourses(distinctSuggestedCourses);
        studentRepository.saveAndFlush(student);
    }

    public void createQuestionsIfNeeded() {
        if (questionRepository.count() != 0) {
            LOG.info("Questions initialized");
            return;
        }

        LOG.info("Creating new questions...");

        Answer javaAnswer = Answer.builder().title("Java").tags(Collections.singletonList("Java")).build();
        Answer pythonAnswer = Answer.builder().title("Python").tags(Collections.singletonList("Python")).build();
        Answer rubyAnswer = Answer.builder().title("Ruby").tags(Collections.singletonList("Ruby")).build();
        Answer phpAnswer = Answer.builder().title("PHP").tags(Collections.singletonList("PHP")).build();

        Question programmingQuestion = Question.builder()
                .title("Какой язык программирования вам нравится или хотели бы изучить?")
                .answers(Arrays.asList(javaAnswer, pythonAnswer, rubyAnswer, phpAnswer))
                .build();

        Answer driversAnswers = Answer.builder()
                .title("Драйвера для разнообразных устройств; хочу работать с железом")
                .tags(Collections.singletonList("Hardware")).build();
        Answer mobileAppsAnswers = Answer.builder()
                .title("Программы и игры для телефонов и планшетов")
                .tags(Arrays.asList("Software", "Mobile"))
                .build();
        Answer webAppsAnswer = Answer.builder()
                .title("Сайты и интернет-проекты")
                .tags(Arrays.asList("Software", "Web"))
                .build();

        Question developmentQuestion = Question.builder()
                .title("Что вы хотите разрабатывать?")
                .answers(Arrays.asList(driversAnswers, mobileAppsAnswers, webAppsAnswer))
                .build();

        Answer mathAnswer = Answer.builder()
                .title("Математический анализ")
                .tags(Arrays.asList("Mathematics", "Analyses", "Data"))
                .build();
        Answer algorithmsAnswer = Answer.builder()
                .title("Алгоритмы и структуры данных")
                .tags(Arrays.asList("Algorithms", "Data"))
                .build();
        Answer economicsAnswer = Answer.builder()
                .title("Экономика")
                .tags(Arrays.asList("Mathematics", "Economics"))
                .build();

        Question favoriteSubjectQuestion = Question.builder()
                .title("Какой из предметов вам легче дается или нравится больше?")
                .answers(Arrays.asList(mathAnswer, algorithmsAnswer, economicsAnswer))
                .build();

        questionRepository.saveAll(Arrays.asList(programmingQuestion, developmentQuestion, favoriteSubjectQuestion));
        questionRepository.flush();

        LOG.info("New questions successfully saved!");
    }

}
