package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class ITISException extends RuntimeException {

    private String message;
    private ErrorCode code;

}
