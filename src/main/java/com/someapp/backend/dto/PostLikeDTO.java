package com.someapp.backend.dto;

import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import java.util.Date;
import java.util.UUID;

public class PostLikeDTO extends BaseDTO {

    private final UUID postId;

    public PostLikeDTO(final UUID uuid, final Date createdDate, final UUID postId) {
        super(uuid, createdDate);
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
