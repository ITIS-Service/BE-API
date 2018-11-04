package com.itis.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    @ApiModelProperty(notes = "Message of response")
    private String message;

    @ApiModelProperty(notes = "Status of response")
    private boolean success;

}
