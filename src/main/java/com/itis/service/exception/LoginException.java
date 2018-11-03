package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class LoginException extends ITISException {

    public LoginException() {
        super("Пользователь с данной парой email/пароль не найден", ErrorCode.LOGIN_ERROR);
    }

}
