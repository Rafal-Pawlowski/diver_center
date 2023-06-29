package com.diver.center.diver_center.exception_handler.InstructorServiceException;

import org.springframework.dao.DataAccessException;

public class GetEntityException extends DataAccessException {
    public GetEntityException(String msg) {
        super(msg);
    }
}
