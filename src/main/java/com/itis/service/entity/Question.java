package com.itis.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
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

    @Column(name = "question_description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(title, question.title) &&
                Objects.equals(description, question.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
