package com.itis.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreatePointDto {

    private String title;
    private String description;
    private Integer count;
    private List<Long> studentIDs;

}
