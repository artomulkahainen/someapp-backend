// @ts-ignore
import { API_URL } from '@env';
import axios, { AxiosResponse } from 'axios';
import { getToken } from '../util/storage/AsyncStorage';
const baseUrl = `${API_URL}/api/v1`;

export interface NewRelationshipRequest {
    addedUserId: string;
}

export interface UpdateRelationshipRequest {
    relationshipId: string;
    status: number;
}

export const saveNewRelationship = async (req: NewRelationshipRequest): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (!!token) {
        try {
            const res = await axios.post(`${baseUrl}/saveNewRelationshipByUsingPOST`, req, {
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

export const updateRelationship = async (req: UpdateRelationshipRequest): Promise<AxiosResponse<any> | null> => {
    const token = await getToken();

    if (!!token) {
        try {
            const res = await axios.put(`${baseUrl}/updateRelationshipByUsingPUT`, req, {
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
