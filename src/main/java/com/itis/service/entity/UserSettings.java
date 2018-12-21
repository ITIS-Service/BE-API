package com.itis.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "user_setting_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "course_status_notification")
    private boolean courseStatusNotificationEnabled = true;

    @Column(name = "points_notification")
    private boolean pointsNotificationEnabled = true;

    @OneToOne(mappedBy = "userSettings", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Student student;

}
