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
@Table(name = "courses_details")
public class CourseDetails {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "course_details_id", length = 6, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

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

    @OneToMany(mappedBy = "courseDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourse> userCourses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courseDetails", orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courseDetails", orphanRemoval = true)
    private List<Request> requests = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDetails that = (CourseDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(course, that.course) &&
                Objects.equals(times, that.times) &&
                Objects.equals(place, that.place) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, times, place, tags, teacher);
    }
}
