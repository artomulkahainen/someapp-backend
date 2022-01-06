package com.someapp.backend.services;

import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.User;
import com.someapp.backend.interfaces.repositories.PostRepository;
import com.someapp.backend.interfaces.repositories.RelationshipRepository;
import com.someapp.backend.interfaces.repositories.UserRepository;
import com.someapp.backend.testUtility.customExceptions.BadArgumentException;
import com.someapp.backend.testUtility.customExceptions.ResourceNotFoundException;
import com.someapp.backend.testUtility.jwt.JWTTokenUtil;
import com.someapp.backend.testUtility.requests.DeletePostRequest;
import com.someapp.backend.testUtility.requests.SendPostRequest;
import com.someapp.backend.testUtility.responses.DeleteResponse;
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
    private RelationshipRepository relationshipRepository;

    @Autowired
    private RelationshipValidator relationshipValidator;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    public Post save(HttpServletRequest req, SendPostRequest sendPostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization"));

        try {
            User user = userRepository.getById(actionUserId);
            return postRepository.save(new Post(sendPostRequest.getPost(), user));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public DeleteResponse delete(HttpServletRequest req, DeletePostRequest deletePostRequest) {
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization"));
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
        UUID actionUserId = jwtTokenUtil.getIdFromToken(req.getHeader("Authorization"));
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
