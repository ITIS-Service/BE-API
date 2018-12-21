package com.itis.service.entity;

import com.itis.service.entity.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {

    @Column(name = "user_passed_quiz")
    private boolean isPassedQuiz = false;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_setting_id")
    private UserSettings userSettings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourse> userCourses = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST
    })
    @JoinTable(name = "students_and_suggested_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Course> suggestedCourses = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "students_and_points",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "point_id"))
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();

    public Student(String email, String password, String firstName, String lastName, Group group, UserSettings userSettings) {
        super(email, password, firstName, lastName, UserRole.STUDENT);
        this.group = group;
        this.userSettings = userSettings;
    }

}
