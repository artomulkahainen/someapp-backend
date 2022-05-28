package com.someapp.backend.utils.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = { BindException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage bindException(BindException ex) {
        ErrorMessage message = new ErrorMessage(
                400,
                new Date(),
                ex.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList()));

        return message;
    }
}
