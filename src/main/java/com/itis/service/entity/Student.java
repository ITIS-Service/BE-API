package com.itis.service.entity;

import com.itis.service.entity.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student(String email, String password, String firstName, String lastName, Group group) {
        super(email, password, firstName, lastName, UserRole.STUDENT);
        this.group = group;
    }

}
