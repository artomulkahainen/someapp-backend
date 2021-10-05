package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostComment;
import com.someapp.backend.services.PostCommentServiceImpl;
import com.someapp.backend.util.requests.SendPostCommentRequest;
import com.someapp.backend.util.requests.UUIDRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class PostCommentController {

    @Autowired
    private PostCommentServiceImpl postCommentService;

    @PostMapping("/sendPostCommentByUsingPOST")
    public PostComment sendNewPostComment(HttpServletRequest req, @Valid @RequestBody SendPostCommentRequest sendPostCommentRequest,
                                          BindingResult bindingResult) throws BindException {
        // IF VALIDATION ERRORS
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postCommentService.save(req, sendPostCommentRequest);
    }

    @PostMapping("/deletePostCommentByUsingPOST")
    public DeleteResponse deletePostCommentById(HttpServletRequest req,
                                                @Valid @RequestBody UUIDRequest postCommentId,
                                                BindingResult bindingResult) throws BindException {

        return postCommentService.delete(req, postCommentId);
    }
}
