package com.master.nst.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<MyErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return new ResponseEntity<>(myErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<MyErrorDetails> handleEntityAlreadyExistsException
            (EntityAlreadyExistsException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<MyErrorDetails> handleDataIntegrityViolationException
            (DataIntegrityViolationException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<MyErrorDetails> handleIllegalArgumentException
            (IllegalArgumentException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }

}
