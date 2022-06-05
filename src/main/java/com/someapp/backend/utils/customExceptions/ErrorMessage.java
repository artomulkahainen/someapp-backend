package com.someapp.backend.utils.customExceptions;

import java.util.Date;
import java.util.List;

public record ErrorMessage(Integer status, Date date, List<String> errors) {

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
