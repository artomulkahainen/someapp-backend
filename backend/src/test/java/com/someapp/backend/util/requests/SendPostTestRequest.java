package com.someapp.backend.util.requests;

public class SendPostTestRequest {

    private String post;

    private String userId;

    public SendPostTestRequest(String post, String userId) {
        this.post = post;
        this.userId = userId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
