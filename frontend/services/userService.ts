// @ts-ignore
import { API_URL } from '@env';
import axios, { AxiosResponse } from 'axios';
import { getToken } from '../AppWrapper';
const baseUrl = `${API_URL}/api/v1`;

export const findMyUserDetails = async (): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (token) {
    const options = {
      headers: { Authorization: `Bearer ${token}` },
    };

    try {
        const res = await axios.get(`${baseUrl}/findOwnUserDetailsByUsingGET`, options);
        return res.data;
    } catch (e: any) {
        console.log(e.response.data);
        return e.response.data;
    }
  }
  return null;
}