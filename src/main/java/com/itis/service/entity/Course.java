package com.itis.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
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

    @Column(name = "course_name")
    private String name;

    @Column(name = "course_description")
    private String description;

    @Column(name = "course_times")
    @ElementCollection
    private List<LocalTime> times = new ArrayList<>();

    @Column(name = "course_place")
    private String place;

    @Column(name = "course_tags")
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourse> userCourses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", orphanRemoval = true)
    private List<Request> requests = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name) &&
                Objects.equals(description, course.description) &&
                Objects.equals(times, course.times) &&
                Objects.equals(place, course.place) &&
                Objects.equals(tags, course.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, times, place, tags);
    }
}
