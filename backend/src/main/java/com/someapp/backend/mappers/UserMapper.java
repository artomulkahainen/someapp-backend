package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.entities.PostLike;
import com.someapp.backend.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper {

    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getUUID(),
                user.getCreatedDate(),
                user.getUsername(),
                user.isAdmin(),
                user.getPosts() != null ? user.getPosts() : new ArrayList<>(),
                user.getPostLikes() != null ? user.getPostLikes()
                        .stream().map(PostLike::getPostUUID)
                        .collect(ImmutableList.toImmutableList()) : new ArrayList<>()
        );
    }
}
