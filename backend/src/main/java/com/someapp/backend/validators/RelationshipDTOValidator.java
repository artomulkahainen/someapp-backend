package com.someapp.backend.validators;

public class RelationshipDTOValidator {

    /**
     *         // If relationship is already created, throw an exception
     *         if (relationshipAlreadyExists(actionUserId, relationshipRequest)) {
     *             throw new BadArgumentException("Relationship is already created.");
     *
     *             // If either user id is not found, throw an exception
     *         }
     */

    /**
     *
     * If relationship is not found from repository, only status 0 is allowed
     */

    /**
     *
     * Action user can send only status 0 (pending requests) and status 2 (declining requests)
     */

    /**
     * Non-action user can only send status 1 (accept request), status 2 (declining requests) or status 3 (block user)
     */

    /**
     * If relationship with given uniqueId is already found, prevent saving
     */


}
