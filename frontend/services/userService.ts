// @ts-ignore
import { API_URL } from '@env';
import axios, { AxiosResponse } from 'axios';
import { getToken } from '../AppWrapper';
import { UserProps } from './loginService';
const baseUrl = `${API_URL}/api/v1`;

export interface MyUserDetails {}

export const findMyUserDetails =
  async (): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (token) {
      try {
        const res = await axios.get(`${baseUrl}/findOwnUserDetailsByUsingGET`, {
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

export const findUsersByName = async (
  username: string
): Promise<AxiosResponse<any> | null> => {
  const token = await getToken();

  if (token) {
    try {
      const res = await axios.post(
        `${baseUrl}/findUsersByNameByUsingPOST`,
        {
          username
        },
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

export const saveNewUser = async (
  creds: UserProps
): Promise<AxiosResponse<any> | null> => {
  try {
    const res = await axios.post(`${baseUrl}/saveNewUserByUsingPOST`, creds);
    return res.data;
  } catch (e: any) {
    console.log(e.response.data);
    return e.response.data;
  }
};
