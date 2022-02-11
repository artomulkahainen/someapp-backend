package com.someapp.backend.mappers;

import com.someapp.backend.dto.UserDTO;
import com.someapp.backend.dto.UserSaveDTO;
import com.someapp.backend.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PostMapper postMapper;
    private final PostLikeMapper postLikeMapper;

    public UserMapper(PostMapper postMapper, PostLikeMapper postLikeMapper) {
        this.postMapper = postMapper;
        this.postLikeMapper = postLikeMapper;
    }

    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getUUID(),
                user.getCreatedDate(),
                user.getUsername(),
                user.isAdmin(),
                postMapper.mapPostsToPostDTOs(user.getPosts()),
                postLikeMapper.mapPostLikesToPostLikeDTOs(user.getPostLikes())
        );
    }

    public User mapUserSaveDTOToUser(UserSaveDTO userSaveDTO) {
        return new User(userSaveDTO.getUsername(), userSaveDTO.getPassword());
    }
}
