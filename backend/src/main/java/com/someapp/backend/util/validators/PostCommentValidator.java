package com.someapp.backend.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostCommentValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public void validate(Object object, Errors errors) {
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


