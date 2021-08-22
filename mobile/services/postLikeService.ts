// @ts-ignore
import { API_URL } from '@env';
import axios, { AxiosResponse } from 'axios';
import { getToken } from '../util/storage/AsyncStorage';
const baseUrl = `${API_URL}/api/v1`;

export interface LikePostRequest {
  postUserId: string;
  postId: string;
}

export interface UnlikePostRequest {
  postLikeId: string;
}

export const likePost = async (
  req: LikePostRequest
): Promise<AxiosResponse<any> | null> => {
  const token = await getToken();

  if (!!token) {
    try {
      const res = await axios.post(`${baseUrl}/likePostByUsingPOST`, req, {
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

export const unLikePost = async (
  req: UnlikePostRequest
): Promise<AxiosResponse<any> | null> => {
  const token = await getToken();

  if (!!token) {
    try {
      const res = await axios.post(`${baseUrl}/unlikePostByUsingPOST`, req, {
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
