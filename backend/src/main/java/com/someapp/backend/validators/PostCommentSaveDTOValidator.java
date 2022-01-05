package com.someapp.backend.validators;

import com.someapp.backend.dto.PostCommentSaveDTO;
import com.someapp.backend.services.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostCommentSaveDTOValidator implements Validator {

    private final PostCommentService postCommentService;

    @Autowired
    private HttpServletRequest req;

    public PostCommentSaveDTOValidator(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PostCommentSaveDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        req.getHeader("Authorization");
        final PostCommentSaveDTO postCommentSaveDTO = (PostCommentSaveDTO) target;

        /** ServletRequest validation
         *
         *         if (actionUserId == null) {
         *             errors.reject("Not authenticated");
         *         }
         */


        /**
         * POST COMMENT SAVE VALIDATION



         // POST CREATOR AND POST COMMENTER MUST HAVE ACTIVE RELATIONSHIP
         if (!relationshipValidator.isActiveRelationship(actionUserId, sendPostCommentRequest.getPostCreatorId())) {
            errors.reject("Active relationship with post creator is needed to write a comment")
         }
         */

        /** POST COMMENT DELETE VALIDATION
         *
         *
         // USERS CAN DELETE ONLY THEIR OWN POST COMMENTS

         if (!commentToDelete.get().getUserId().equals(actionUserId)) {
            errors.reject("Users can delete only their own post comments");
         }

        */
    }


}


