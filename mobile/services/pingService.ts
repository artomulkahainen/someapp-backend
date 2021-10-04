// @ts-ignore
import { API_URL } from '@env';
import axios from 'axios';
const baseUrl = `${API_URL}/api/v1/ping`;

export interface ServerStatus {
    date: Date;
    httpStatus: string;
}

export const ping = () => {
    return axios
        .get(baseUrl)
        .then((res: any) => res.data)
        .catch(() => {
            console.log('cannot connect to server');
            return { date: new Date(), httpStatus: 'NOT_FOUND' };
        });
};
