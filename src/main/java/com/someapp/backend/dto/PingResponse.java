package com.someapp.backend.dto;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class PingResponse {

    @NotNull
    private final HttpStatus httpStatus;
    @NotNull
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
