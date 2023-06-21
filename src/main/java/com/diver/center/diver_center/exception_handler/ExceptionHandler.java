package com.diver.center.diver_center.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(AttachedException.class)
    public Error handleAttachedException(final AttachedException exception){
        log.debug("Cannot remove entity that is connected with childEntity, suppose to detach");
        return new Error(exception.getMessage());
    }
}
