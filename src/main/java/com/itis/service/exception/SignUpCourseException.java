package com.itis.service.exception;

import com.itis.service.exception.codes.ErrorCode;

public class SignUpCourseException extends ITISException {

    public SignUpCourseException(boolean limit) {
        super("Студент уже записан на этот курс", ErrorCode.COURSE_SIGN_UP);

        if (limit) {
            this.message = "Студент уже записан на максимальное количетсво курсов";
            this.code = ErrorCode.COURSE_SIGN_UP_LIMIT;
        }
    }

    public SignUpCourseException() {
        this(false);
    }

}
