import axios from 'axios';
const baseUrl = '/api/v1/login';

interface LoginProps {
    username: string;
    password: string;
}

export const login = async (credentials: LoginProps): Promise<any> => {
    const res = await axios.post(baseUrl, credentials);
    return res.data;
}