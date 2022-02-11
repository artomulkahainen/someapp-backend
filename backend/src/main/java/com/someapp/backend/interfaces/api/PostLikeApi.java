package com.someapp.backend.interfaces.api;

import com.someapp.backend.dto.PostLikeDTO;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface PostLikeApi {

    @PostMapping("/likePostByUsingPOST")
    PostLikeDTO likePost(@Valid @RequestBody LikePostRequest likePostRequest,
                         BindingResult bindingResult) throws BindException;

    @PostMapping("/unlikePostByUsingPOST")
    DeleteResponse unlikePost(@Valid @RequestBody UnlikePostRequest unlikePostRequest,
                              BindingResult bindingResult) throws BindException;
}
