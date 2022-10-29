package com.someapp.backend.services;

import com.someapp.backend.entities.PostLike;
import com.someapp.backend.dto.LikePostRequest;
import com.someapp.backend.dto.UnlikePostRequest;
import com.someapp.backend.dto.DeleteResponse;

import java.util.Optional;
import java.util.UUID;

public interface PostLikeService {

    PostLike save(LikePostRequest likePostRequest);

    DeleteResponse delete(UnlikePostRequest unlikePostRequest);

    boolean likeAlreadyExists(UUID actionUserId,
                              LikePostRequest likePostRequest);

    Optional<PostLike> findPostLikeById(UUID uuid);
}
