package com.itis.service.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    @Column(name = "teacher_link")
    private String link;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher")
    private List<Course> courses;

}
