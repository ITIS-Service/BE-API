package com.itis.service.dto;

import com.itis.service.exception.ITISException;
import com.itis.service.exception.codes.ErrorCode;
import lombok.Data;

@Data
public class ExceptionResponseDto {

    private String message;
    private ErrorCode errorCode;

    public ExceptionResponseDto(ITISException e) {
        this.message = e.getMessage();
        this.errorCode = e.getCode();
    }

}
