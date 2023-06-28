package com.diver.center.diver_center.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class AttachedExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AttachedException.class)
    public ResponseEntity<String> handleAttachedException(final AttachedException exception){
        log.debug("Cannot remove entity that is connected with childEntity, suppose to detach");

        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
