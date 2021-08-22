// @ts-ignore
import { API_URL } from '@env';
import axios from 'axios';
const baseUrl = `${API_URL}/api/v1/loginByUsingPOST`;

export interface SuccessfulLoginResponse {
  token: string;
}

export interface UserProps {
  username: string;
  password: string;
}

export const login = async (creds: UserProps) => {
  const res = await axios.post(baseUrl, creds);
  return res.data;
};
