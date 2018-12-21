package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class CreateResourceException extends ITISException {

    public CreateResourceException(String message) {
        super(message, ErrorCode.CREATE_RESOURCE);
    }

}
