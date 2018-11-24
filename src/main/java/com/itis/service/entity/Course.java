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
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "course_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "course_name", unique = true)
    private String name;

    @Column(name = "course_description", length = 512)
    private String description;

    @Column(name = "course_tags")
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @Column(name = "course_number")
    private Integer number;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private CourseDetails courseDetails;

    public Course(String name, String description, List<String> tags, Integer number) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name) &&
                Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
