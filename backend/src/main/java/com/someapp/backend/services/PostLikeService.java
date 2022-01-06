package com.someapp.backend.services;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.utils.requests.LikePostRequest;
import com.someapp.backend.utils.requests.UnlikePostRequest;
import com.someapp.backend.utils.responses.DeleteResponse;

import javax.servlet.http.HttpServletRequest;

public interface PostLikeService {

    PostLike save(HttpServletRequest req, LikePostRequest likePostRequest);

    DeleteResponse delete(HttpServletRequest req, UnlikePostRequest unlikePostRequest);
}
