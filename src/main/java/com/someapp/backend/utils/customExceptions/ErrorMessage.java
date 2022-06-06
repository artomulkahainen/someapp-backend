package com.someapp.backend.utils.customExceptions;

import java.util.Date;
import java.util.List;

public class ErrorMessage {

    private Integer status;
    private Date date;
    private List<String> errors;

    public ErrorMessage(Integer status, Date date, List<String> errors) {
        this.status = status;
        this.date = date;
        this.errors = errors;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getErrors() {
        return errors;
    }
}
