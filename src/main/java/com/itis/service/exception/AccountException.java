package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class AccountException extends ITISException {

    public AccountException() {
        super("Неверный старый пароль", ErrorCode.INCORRECT_PASSWORD);
    }

}
