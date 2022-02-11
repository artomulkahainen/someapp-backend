package com.someapp.backend.services;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.entities.PostComment;
import com.someapp.backend.interfaces.repositories.PostCommentRepository;
import com.someapp.backend.mappers.PostCommentMapper;
import com.someapp.backend.utils.jwt.JWTTokenUtil;
import com.someapp.backend.dto.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostCommentMapper postCommentMapper;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest req;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
                                  PostCommentMapper postCommentMapper,
                                  JWTTokenUtil jwtTokenUtil) {
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public PostComment save(PostCommentSaveDTO postCommentSaveDTO) {
        return postCommentRepository.save(postCommentMapper.mapPostCommentSaveDTOToPostComment(
                jwtTokenUtil.getIdFromToken(req), postCommentSaveDTO));
    }

    @Override
    public DeleteResponse delete(UUID postCommentId) {
        PostComment commentToDelete = postCommentRepository.findById(postCommentId)
                .orElseThrow(ResourceNotFoundException::new);

        postCommentRepository.deleteById(commentToDelete.getId());
        return new DeleteResponse(postCommentId, "Successfully deleted post comment");
    }
}
