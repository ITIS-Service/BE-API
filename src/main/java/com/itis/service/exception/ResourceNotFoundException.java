package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class ResourceNotFoundException extends ITISException {

    public ResourceNotFoundException(String message) {
        super(message, ErrorCode.RESOURCE_NOT_FOUND);
    }

}
