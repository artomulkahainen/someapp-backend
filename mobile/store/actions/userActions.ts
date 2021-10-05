import * as actionTypes from './actionTypes';

export const updateUserDetails = (updatedObject: any) => ({
    type: actionTypes.UPDATE_USER_DETAILS,
    data: updatedObject
});

export const removeUserDetails = () => ({ type: actionTypes.REMOVE_USER_DETAILS, data: [] });
