package com.someapp.backend.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SendPostRequest {

    @Size(min = 1, max = 250, message = "Post length must be between 1-250 letters.")
    @NotNull
    @NotEmpty
    private String post;

    public SendPostRequest(String post) {
        this.post = post;
    }

    public String getPost() {
        return post;
    }


}
