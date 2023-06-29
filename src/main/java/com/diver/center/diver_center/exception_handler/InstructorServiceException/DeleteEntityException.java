package com.diver.center.diver_center.exception_handler.InstructorServiceException;

import org.springframework.dao.DataAccessException;

public class DeleteEntityException extends DataAccessException {
    public DeleteEntityException(String msg) {
        super(msg);
    }
}
