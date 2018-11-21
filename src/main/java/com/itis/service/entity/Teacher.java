package com.itis.service.entity;

import com.itis.service.entity.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    @Column(name = "teacher_link")
    private URL link;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher")
    private List<CourseDetails> courseDetails;

    public Teacher(String email, String firstName, String lastName, URL link) {
        super(email, null, firstName, lastName, UserRole.TEACHER);
        this.link = link;
    }

}
