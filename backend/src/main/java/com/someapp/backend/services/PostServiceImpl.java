package com.someapp.backend.services;

import com.google.common.collect.ImmutableList;
import com.someapp.backend.dto.DeletePostRequest;
import com.someapp.backend.dto.SendPostRequest;
import com.someapp.backend.entities.Post;
import com.someapp.backend.entities.Relationship;
import com.someapp.backend.repositories.PostRepository;
import com.someapp.backend.repositories.RelationshipRepository;
import com.someapp.backend.repositories.UserRepository;
import com.someapp.backend.mappers.PostMapper;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.dto.DeleteResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

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
    private JWTTokenUtil jwtTokenUtil;

    @Override
    public Post save(final SendPostRequest sendPostRequest) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final UUID actionUserId = jwtTokenUtil.getIdFromToken(req);
        return postRepository.save(postMapper.mapSendPostRequestToPost(actionUserId, sendPostRequest));
    }

    @Override
    public DeleteResponse delete(final DeletePostRequest deletePostRequest) {
        final Post postToDelete = postRepository.findById(deletePostRequest.getUuid())
                .orElseThrow(ResourceNotFoundException::new);
        postRepository.delete(postToDelete);
        return new DeleteResponse(deletePostRequest.getUuid(), "Successfully deleted post");
    }

    @Override
    public Optional<Post> findPostById(final UUID uuid) {
        return postRepository.findById(uuid);
    }

    @Override
    public List<Post> findPostsByRelationships() {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        final List<UUID> ownActiveRelationships = relationshipRepository
                .findRelationshipsByuser_id(jwtTokenUtil.getIdFromToken(req))
                .stream()
                .filter(relationship -> relationship.getStatus() == 1)
                .map(Relationship::getRelationshipWith)
                .collect(ImmutableList.toImmutableList());

        final Comparator<Post> byCreatedDate = Comparator.comparing(Post::getCreatedDate).reversed();

        return postRepository
                .findAll()
                .stream()
                .filter(post -> ownActiveRelationships.contains(post.getUserId()))
                .sorted(byCreatedDate).limit(10)
                .collect(ImmutableList.toImmutableList());
    }
}
