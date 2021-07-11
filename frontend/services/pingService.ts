import axios from 'axios';
const baseUrl = 'http://localhost:8080/api/v1/ping';

export const ping = async () => {
    const res = await axios.get(baseUrl);
    console.log(res);
    return res.data;
}