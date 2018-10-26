package com.itis.service.entity;

import com.itis.service.entity.enums.RequestStatus;
import com.itis.service.entity.enums.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "request_id", length = 6, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "request_name")
    private String name;

    @Column(name = "request_changes")
    private String changes;

    @Column(name = "request_date")
    private Date date;

    @Column(name = "request_type")
    private RequestType type;

    @Column(name = "request_status")
    private RequestStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(name, request.name) &&
                Objects.equals(changes, request.changes) &&
                Objects.equals(date, request.date) &&
                type == request.type &&
                status == request.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, changes, date, type, status);
    }
}
