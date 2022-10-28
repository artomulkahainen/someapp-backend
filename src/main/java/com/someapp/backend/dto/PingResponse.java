package com.someapp.backend.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class PingResponse {

    private final HttpStatus httpStatus;
    private final Date date;

    public PingResponse(final HttpStatus httpStatus, final Date date) {
        this.httpStatus = httpStatus;
        this.date = date;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Date getDate() {
        return date;
    }
}
