package ru.unn.smartnet.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NetExceptionController {
    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<Object> notFoundException(EmptyResultDataAccessException exception) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
