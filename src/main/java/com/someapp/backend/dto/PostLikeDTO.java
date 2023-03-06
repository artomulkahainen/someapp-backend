package com.someapp.backend.dto;

import com.someapp.backend.dto.abstractDTOs.BaseDTO;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class PostLikeDTO extends BaseDTO {

    @NotNull
    private final UUID postId;
    @NotNull
    private final UUID likerId;

    public PostLikeDTO(final UUID uuid, final Date createdDate, final UUID postId, final UUID likerId) {
        super(uuid, createdDate);
        this.postId = postId;
        this.likerId = likerId;
    }

    public UUID getPostId() {
        return postId;
    }
    public UUID getLikerId() {
        return likerId;
    }
}
