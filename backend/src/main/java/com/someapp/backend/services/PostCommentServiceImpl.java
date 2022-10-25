package com.someapp.backend.services;

import com.someapp.backend.dto.DeleteResponse;
import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.mappers.PostCommentMapper;
import com.someapp.backend.repositories.PostCommentRepository;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostCommentMapper postCommentMapper;
    private final JWTTokenUtil jwtTokenUtil;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
                                  PostCommentMapper postCommentMapper,
                                  JWTTokenUtil jwtTokenUtil) {
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public PostComment save(final PostCommentSaveDTO postCommentSaveDTO) {
        final HttpServletRequest req = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        return postCommentRepository.save(postCommentMapper.mapPostCommentSaveDTOToPostComment(
                jwtTokenUtil.getIdFromToken(req), postCommentSaveDTO));
    }

    @Override
    public DeleteResponse delete(final UUID postCommentId) {
        PostComment commentToDelete = postCommentRepository.findById(postCommentId)
                .orElseThrow(ResourceNotFoundException::new);

        postCommentRepository.deleteById(commentToDelete.getId());
        return new DeleteResponse(postCommentId, "Successfully deleted post comment");
    }
}
