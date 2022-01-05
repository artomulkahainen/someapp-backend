package com.someapp.backend.mappers;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.entities.User;

public class UserMapper {

    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getUUID(),
                user.getCreatedDate(),
                user.getUsername(),
                user.isAdmin(),
                user.getPosts(),
                user.getPostLikes()
                        .stream().map(like -> like.getPostUUID())
                        .collect(ImmutableList.toImmutableList())
        );
    }
}
