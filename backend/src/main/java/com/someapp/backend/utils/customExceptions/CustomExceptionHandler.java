package com.someapp.backend.utils.customExceptions;

import com.google.common.collect.ImmutableList;
import io.jsonwebtoken.JwtException;
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

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage bindException(BindException ex) {
        return new ErrorMessage(
                400,
                new Date(),
                ex.getAllErrors().stream().map(ObjectError::getCode).collect(Collectors.toList()));
    }

    @ExceptionHandler(BadArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage badArgumentException(BadArgumentException ex) {
        return new ErrorMessage(400, new Date(), ImmutableList.of(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorMessage(404, new Date(), ImmutableList.of(ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage signatureException(JwtException ex) {
        return new ErrorMessage(401, new Date(), ImmutableList.of(ex.getMessage()));
    }
}
