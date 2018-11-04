package com.itis.service.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter(value = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "question_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "question_title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public static QuestionBuilder builder() {
        return new CustomQuestionBuilder();
    }

    public static class CustomQuestionBuilder extends QuestionBuilder {

        @Override
        public Question build() {
            Question question = super.build();
            for (Answer answer : super.answers) {
                answer.setQuestion(question);
            }
            return question;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(title, question.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
