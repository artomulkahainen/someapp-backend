package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.interfaces.api.PostLikeApi;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.testUtility.requests.LikePostRequest;
import com.someapp.backend.testUtility.requests.UnlikePostRequest;
import com.someapp.backend.testUtility.responses.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PostLikeController implements PostLikeApi {

    @Autowired
    private PostLikeService postLikeService;

    @Override
    public PostLike likePost(HttpServletRequest req, LikePostRequest likePostRequest,
                             BindingResult bindingResult) throws BindException {

        // IF VALIDATION ERRORS, THROW AN EXCEPTION
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.save(req, likePostRequest);
    }

    @Override
    public DeleteResponse unlikePost(HttpServletRequest req,
                                     UnlikePostRequest unlikePostRequest,
                                     BindingResult bindingResult) throws BindException {

        // IF VALIDATION ERRORS, THROW AN EXCEPTION
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.delete(req, unlikePostRequest);
    }
}
