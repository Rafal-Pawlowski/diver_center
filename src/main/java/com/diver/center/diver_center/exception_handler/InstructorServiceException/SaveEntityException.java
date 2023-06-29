package com.diver.center.diver_center.exception_handler.InstructorServiceException;

import org.springframework.dao.DataAccessException;

public class SaveEntityException extends DataAccessException {
    public SaveEntityException(String msg) {
        super(msg);
    }
}
