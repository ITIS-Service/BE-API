package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class InitializeException extends ITISException {

    public InitializeException() {
        super("Не удалось загрузить список студентов с сайта КФУ", ErrorCode.INITIALIZE_ERROR);
    }

}
