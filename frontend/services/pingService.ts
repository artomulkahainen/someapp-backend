// @ts-ignore
import { API_URL } from '@env';
import axios from 'axios';
const baseUrl = `${API_URL}/api/v1/ping`;

export const ping = async () => {
    const res = await axios.get(baseUrl);
    return res.data;
}