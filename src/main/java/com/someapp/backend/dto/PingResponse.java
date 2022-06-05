package com.someapp.backend.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class PingResponse {

    private HttpStatus httpStatus;
    private Date date;

    public PingResponse(HttpStatus httpStatus, Date date) {
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
