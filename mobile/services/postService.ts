// @ts-ignore
import { API_URL } from '@env';
import axios, { AxiosResponse } from 'axios';
import { getToken } from '../util/storage/AsyncStorage';
const baseUrl = `${API_URL}/api/v1`;

export interface PostDTO {
    post: string;
}

export const getFriendsPosts = async (): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (token) {
        try {
            const res = await axios.get(`${baseUrl}/getPostsByRelationshipsByUsingGET`, {
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

export const sendNewPost = async (postDTO: PostDTO): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (token) {
        try {
            const res = await axios.post(`${baseUrl}/sendNewPostByUsingPOST`, postDTO, {
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

export const deletePost = async (postId: string): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (token) {
        try {
            const res = await axios.post(
                `${baseUrl}/deletePostByUsingPOST`,
                { postId },
                { headers: { Authorization: `Bearer ${token}` } }
            );

            return res.data;
        } catch (e: any) {
            console.log(e.response.data);
            return e.response.data;
        }
    }

    return null;
};
