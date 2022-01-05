package com.someapp.backend.interfaces.api;

import com.someapp.backend.entities.PostComment;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PostCommentApi {

    @PostMapping("/sendPostCommentByUsingPOST")
    PostComment sendNewPostComment(@NotNull HttpServletRequest req, @Valid @RequestBody SendPostCommentRequest sendPostCommentRequest,
                                   BindingResult bindingResult) throws BindException;

    @PostMapping("/deletePostCommentByUsingPOST")
    DeleteResponse deletePostCommentById(@NotNull HttpServletRequest req,
                                         @Valid @RequestBody UUIDRequest postCommentId,
                                         BindingResult bindingResult) throws BindException;
}
