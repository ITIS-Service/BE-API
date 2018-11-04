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
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "group_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "group_name", unique = true)
    private String name;

    @Column(name = "group_course", nullable = false)
    private Integer course;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    public Group(String name, Integer course) {
        this.name = name;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) &&
                Objects.equals(course, group.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course);
    }
}
