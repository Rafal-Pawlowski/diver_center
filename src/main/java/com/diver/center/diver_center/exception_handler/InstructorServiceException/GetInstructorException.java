package com.diver.center.diver_center.exception_handler.InstructorServiceException;

import org.springframework.dao.DataAccessException;

public class GetInstructorException extends DataAccessException {
    public GetInstructorException(String msg) {
        super(msg);
    }
}
