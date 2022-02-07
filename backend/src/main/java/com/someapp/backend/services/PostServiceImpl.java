package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.mappers.PostMapper;
import com.someapp.backend.utils.customExceptions.BadArgumentException;
import com.someapp.backend.utils.customExceptions.ResourceNotFoundException;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.utils.requests.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.utils.responses.DeleteResponse;
import com.someapp.backend.validators.RelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private RelationshipValidator relationshipValidator;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    public Post save(HttpServletRequest req, SendPostRequest sendPostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        return postRepository.save(postMapper.mapSendPostRequestToPost(actionUserId, sendPostRequest));
    }

    @Override
    public DeleteResponse delete(HttpServletRequest req, DeletePostRequest deletePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        Optional<Post> postToDelete = postRepository.findById(deletePostRequest.getPostId());

        if (postToDelete.isPresent() && !postToDelete.get().getUserId().equals(actionUserId)) {
            throw new BadArgumentException("Posts can be deleted only by owners.");
        }

        try {
            postRepository.deleteById(deletePostRequest.getPostId());
            return new DeleteResponse(deletePostRequest.getPostId(), "Successfully deleted post");
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Post was not found");
        }
    }

    @Override
    public List<Post> findPostsByRelationships(HttpServletRequest req) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req);

        // Change this to use BooleanBuilder and QPost querys
        Set<UUID> friendIds = relationshipRepository
                .findAll()
                .stream()
                .filter(relationship -> relationshipValidator.isUserInActiveRelationship(actionUserId, relationship))
                .map(relationship -> relationship.getUser1().getUUID().equals(actionUserId) ?
                        relationship.getUser1().getUUID() : relationship.getUser2().getUUID())
                .collect(Collectors.toSet());

        Comparator<Post> byCreatedDate = Comparator.comparing(Post::getCreatedDate).reversed();

        return postRepository
                .findAll()
                .stream()
                .filter(post -> friendIds
                        .stream()
                        .anyMatch(friendId -> friendId.equals(post.getUserId())))
                .sorted(byCreatedDate).limit(10)
                .collect(Collectors.toList());
    }
}
