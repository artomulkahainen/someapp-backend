package com.someapp.backend.controllers;

import com.someapp.backend.dto.PostLikeDTO;
import com.someapp.backend.api.PostLikeApi;
import com.someapp.backend.mappers.PostLikeMapper;
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

@RestController
public class PostLikeController implements PostLikeApi {

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Autowired
    private LikePostRequestValidator likePostRequestValidator;

    @Autowired
    private UnlikePostRequestValidator unlikeValidator;

    @Override
    public PostLikeDTO likePost(final LikePostRequest likePostRequest,
                                final BindingResult bindingResult)
            throws BindException {
        likePostRequestValidator.validate(likePostRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeMapper.mapPostLikeToPostLikeDTO(
                postLikeService.save(likePostRequest));
    }

    @Override
    public DeleteResponse unlikePost(final UnlikePostRequest unlikePostRequest,
                                     final BindingResult bindingResult)
            throws Exception {
        unlikeValidator.validate(unlikePostRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return postLikeService.delete(unlikePostRequest);
    }
}
