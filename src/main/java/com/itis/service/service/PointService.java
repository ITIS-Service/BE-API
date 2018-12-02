package com.itis.service.service;

import com.itis.service.dto.CreatePointDto;
import com.itis.service.dto.PointDto;
import com.itis.service.dto.UserPointsDto;

public interface PointService {

    PointDto createPoint(CreatePointDto createPointDto, long courseID);
    UserPointsDto fetchPoints(long courseID, String email);

}
