package com.itis.service.mapper;

import com.itis.service.dto.ProfileDto;
import com.itis.service.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    ProfileDto profileDto(Student student);

}
