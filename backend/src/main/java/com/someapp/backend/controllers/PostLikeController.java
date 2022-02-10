package com.someapp.backend.controllers;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.interfaces.api.PostLikeApi;
import com.someapp.backend.services.PostLikeService;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.validators.LikePostRequestValidator;
import com.someapp.backend.validators.UnlikePostRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PostLikeController implements PostLikeApi {

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private LikePostRequestValidator likePostRequestValidator;

    @Autowired
    private UnlikePostRequestValidator unlikeValidator;

    @Override
    public PostLike likePost(HttpServletRequest req, LikePostRequest likePostRequest,
                             BindingResult bindingResult) throws BindException {
        likePostRequestValidator.validate(likePostRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.save(req, likePostRequest);
    }

    @Override
    public DeleteResponse unlikePost(HttpServletRequest req,
                                     UnlikePostRequest unlikePostRequest,
                                     BindingResult bindingResult) throws BindException {
        unlikeValidator.validate(unlikePostRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.delete(unlikePostRequest);
    }
}
