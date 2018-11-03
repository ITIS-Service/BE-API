package com.itis.service.entity;

import com.itis.service.entity.enums.UserCourseStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users_and_courses")
public class UserCourse {

    @Embeddable
    @AllArgsConstructor
    @Data
    public static class UserCourseId implements Serializable {

        @Column(name = "user_id")
        private Long userID;

        @Column(name = "course_id")
        private Long courseID;

    }

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseID")
    private CourseDetails courseDetails;

    @Column(name = "status")
    private UserCourseStatus status;

}
