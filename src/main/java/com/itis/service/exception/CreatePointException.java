package com.itis.service.exception;

import com.itis.service.entity.Student;
import com.itis.service.exception.codes.ErrorCode;

public class CreatePointException extends ITISException {

    public CreatePointException(Student student) {
        super(String.format("Студент %s %s не записан на данный курс",
                student.getFirstName(),
                student.getLastName()),
                ErrorCode.CREATE_POINT);
    }

    public CreatePointException(String message) {
        super(message, ErrorCode.CREATE_POINT);
    }

}
