package com.someapp.backend.interfaces.api;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PostLikeApi {

    @PostMapping("/likePostByUsingPOST")
    PostLike likePost(@NotNull HttpServletRequest req, @Valid @RequestBody LikePostRequest likePostRequest,
                      BindingResult bindingResult) throws BindException;

    @PostMapping("/unlikePostByUsingPOST")
    DeleteResponse unlikePost(@NotNull HttpServletRequest req,
                              @Valid @RequestBody UnlikePostRequest unlikePostRequest,
                              BindingResult bindingResult) throws BindException;
}
