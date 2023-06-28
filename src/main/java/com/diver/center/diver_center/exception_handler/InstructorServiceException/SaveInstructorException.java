package com.diver.center.diver_center.exception_handler.InstructorServiceException;

import org.springframework.dao.DataAccessException;

public class SaveInstructorException extends DataAccessException {
    public SaveInstructorException(String msg) {
        super(msg);
    }
}
