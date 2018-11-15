package com.itis.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class AcceptAnswersDto {

    private Map<Long, Long> answers;

}
