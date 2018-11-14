package com.itis.service.dto;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestDtoList {

    private List<RequestDto> requestDtos;
}
