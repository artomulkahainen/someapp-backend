import React from 'react';
import { Card } from 'react-native-elements';
import { Text } from 'react-native';

export interface PostComponentProps {
    author: string;
    post: string;
}

const Post = (props: PostComponentProps) => {
    return (
        <Card>
            <Card.Title>{props.author}</Card.Title>
            <Card.Divider />
            <Text style={{ textAlign: 'center' }}>{props.post}</Text>
        </Card>
    );
};

export default Post;
