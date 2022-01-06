package com.someapp.backend.validators;

public class PostCommentDeleteDTOValidator {

    /** POST COMMENT DELETE VALIDATION
     *
     *
     // USERS CAN DELETE ONLY THEIR OWN POST COMMENTS

     if (!commentToDelete.get().getUserId().equals(actionUserId)) {
     errors.reject("Users can delete only their own post comments");
     }

     */

}
