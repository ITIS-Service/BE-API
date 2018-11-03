package com.itis.service.repository;

import com.itis.service.entity.CourseDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<CourseDetails, Long> {

}
