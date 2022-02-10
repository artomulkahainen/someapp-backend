package com.someapp.backend.services;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.utils.requests.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface PostLikeService {

    PostLike save(HttpServletRequest req, LikePostRequest likePostRequest);

    DeleteResponse delete(UnlikePostRequest unlikePostRequest);

    boolean likeAlreadyExists(UUID actionUserId, LikePostRequest likePostRequest);
}
