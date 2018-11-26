package com.itis.service.entity;

import com.itis.service.entity.enums.Day;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
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

    @Column(name = "times")
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LocalTime> times;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_details_id")
    private CourseDetails courseDetails;

    public DayTime(Day day, List<LocalTime> times) {
        this.day = day;
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTime dayTime = (DayTime) o;
        return Objects.equals(id, dayTime.id) &&
                day == dayTime.day &&
                Objects.equals(times, dayTime.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, times);
    }
}

