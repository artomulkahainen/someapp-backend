package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.util.customExceptions.BadArgumentException;
import com.someapp.backend.util.customExceptions.ResourceNotFoundException;
import com.someapp.backend.util.jwt.JWTTokenUtil;
import com.someapp.backend.util.requests.DeletePostRequest;
import com.someapp.backend.util.requests.SendPostRequest;
import com.someapp.backend.util.responses.DeleteResponse;
import com.someapp.backend.util.validators.RelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private RelationshipValidator relationshipValidator;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    public Post save(HttpServletRequest req, SendPostRequest sendPostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));

        try {
            User user = userRepository.getById(actionUserId);
            return postRepository.save(new Post(sendPostRequest.getPost(), user));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public DeleteResponse delete(HttpServletRequest req, DeletePostRequest deletePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
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

    // Currently fetches all posts matching the criteria
    public List<Post> findPostsByRelationships(HttpServletRequest req) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization").substring(7));
        Set<UUID> friendIds = relationshipRepository
                .findAll()
                .stream()
                .filter(relationship -> relationshipValidator.isUserInActiveRelationship(actionUserId, relationship))
                .map(relationship -> relationship.getUser1().getUUID().equals(actionUserId) ?
                        relationship.getUser1().getUUID() : relationship.getUser2().getUUID())
                .collect(Collectors.toSet());

        Comparator<Post> byCreatedDate = Comparator.comparing(Post::getCreatedDate);

        return postRepository
                .findAll()
                .stream()
                .filter(post -> friendIds
                        .stream()
                        .anyMatch(friendId -> friendId.equals(post.getUserId())))
                .sorted(byCreatedDate)
                .collect(Collectors.toList());
    }
}
