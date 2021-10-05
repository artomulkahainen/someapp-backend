package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.services.PostLikeServiceImpl;
import com.someapp.backend.util.requests.LikePostRequest;
import com.someapp.backend.util.requests.UnlikePostRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class PostLikeController {

    @Autowired
    private PostLikeServiceImpl postLikeService;

    @PostMapping("/likePostByUsingPOST")
    public PostLike likePost(HttpServletRequest req, @Valid @RequestBody LikePostRequest likePostRequest,
                             BindingResult bindingResult) throws BindException {

        // IF VALIDATION ERRORS, THROW AN EXCEPTION
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.save(req, likePostRequest);
    }

    @PostMapping("/unlikePostByUsingPOST")
    public DeleteResponse unlikePost(HttpServletRequest req,
                                     @Valid @RequestBody UnlikePostRequest unlikePostRequest,
                                     BindingResult bindingResult) throws BindException {

        // IF VALIDATION ERRORS, THROW AN EXCEPTION
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.delete(req, unlikePostRequest);
    }
}
