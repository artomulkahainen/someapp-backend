// @ts-ignore
import { API_URL } from '@env';
import axios, { AxiosResponse } from 'axios';
import { getToken } from '../util/storage/AsyncStorage';
const baseUrl = `${API_URL}/api/v1`;

export interface PostCommentRequest {
    postComment: string;
    postCreatorId: string;
    postId: string;
}

export interface UUIDRequest {
    uuid: string;
}

export const sendNewPostComment = async (req: PostCommentRequest): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (!!token) {
        try {
            const res = await axios.post(`${baseUrl}/sendPostCommentByUsingPOST`, req, {
                headers: { Authorization: `Bearer ${token}` }
            });
            return res.data;
        } catch (e: any) {
            console.log(e.response.data);
            return e.response.data;
        }
    }

    return null;
};

export const deletePostComment = async (req: UUIDRequest): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (!!token) {
        try {
            const res = await axios.post(`${baseUrl}/deletePostCommentByUsingPOST`, req, {
                headers: { Authorization: `Bearer ${token}` }
            });
            return res.data;
        } catch (e: any) {
            console.log(e.response.data);
            return e.response.data;
        }
    }

    return null;
};
