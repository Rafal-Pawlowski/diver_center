package com.diver.center.diver_center.exception_handler.InstructorServiceException;


public class NoExistingEntityException extends IllegalArgumentException {

    public NoExistingEntityException(String msg){
        super(msg);
    }
}
