package com.itis.service.controller;

import com.itis.service.dto.ExceptionResponseDto;
import com.itis.service.exception.ITISException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandlerController {

    @ExceptionHandler(ITISException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponseDto handleITISException(ITISException e) {
        return new ExceptionResponseDto(e);
    }

}
