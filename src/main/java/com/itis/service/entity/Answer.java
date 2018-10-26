package com.itis.service.entity;

import lombok.Data;
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
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "answer_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "answer_title")
    private String title;

    @Column(name = "answer_tags")
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) &&
                Objects.equals(title, answer.title) &&
                Objects.equals(tags, answer.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tags);
    }
}
