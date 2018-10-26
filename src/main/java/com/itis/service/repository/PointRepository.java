package com.itis.service.repository;

import com.itis.service.entity.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends CrudRepository<Point, Long> {

}
