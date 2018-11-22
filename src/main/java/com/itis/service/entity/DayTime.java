package com.itis.service.entity;

import com.itis.service.entity.enums.Day;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "day_times")
public class DayTime {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "day_time_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "day")
    private Day day;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_details_id")
    private CourseDetails courseDetails;

    public DayTime(Day day, LocalTime time) {
        this.day = day;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTime dayTime = (DayTime) o;
        return Objects.equals(id, dayTime.id) &&
                day == dayTime.day &&
                Objects.equals(time, dayTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, time);
    }
}

