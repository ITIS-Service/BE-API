package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class SignOutCourseException extends ITISException {

    public SignOutCourseException() {
        super("Невозможно выйти из курса, вы уже приняты", ErrorCode.COURSE_SIGN_OUT);
    }

}
