package com.someapp.backend.services;

import com.someapp.backend.entities.PostComment;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;

public interface PostCommentService {

    PostComment save(HttpServletRequest req, SendPostCommentRequest sendPostCommentRequest);

    DeleteResponse delete(HttpServletRequest req, UUIDRequest postCommentId);
}
