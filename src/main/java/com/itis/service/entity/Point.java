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
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "point_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "point_title")
    private String title;

    @Column(name = "point_description")
    private String description;

    @Column(name = "point_count")
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseDetails courseDetails;

    @ManyToMany(mappedBy = "points")
    private List<Student> users = new ArrayList<>();

    public Point(String title, String description, Integer count, CourseDetails courseDetails) {
        this.title = title;
        this.description = description;
        this.count = count;
        this.courseDetails = courseDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(id, point.id) &&
                Objects.equals(title, point.title) &&
                Objects.equals(description, point.description) &&
                Objects.equals(count, point.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, count);
    }
}
