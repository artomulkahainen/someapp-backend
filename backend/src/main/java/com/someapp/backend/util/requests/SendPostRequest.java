package com.someapp.backend.util.requests;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

public class SendPostRequest {

    @Size(min = 1, max = 250, message = "Post length must be between 1-250 letters.")
    @NotNull
    @NotEmpty
    private String post;
    private UUID userId;

    public SendPostRequest(String post, UUID userId) {
        this.post = post;
        this.userId = userId;
    }

    public String getPost() {
        return post;
    }

    public UUID getUserId() {
        return userId;
    }

}
