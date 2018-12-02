package com.itis.service.mapper;

import com.itis.service.dto.PointDto;
import com.itis.service.entity.Point;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {

    PointDto pointDto(Point point);

    List<PointDto> pointDtoList(List<Point> points);

}
