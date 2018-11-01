package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class RegistrationException extends ITISException {

    public RegistrationException() {
        super("Пользователь с данным email адресом уже зарегистрирован", ErrorCode.ALREADY_REGISTERED);
    }

}
