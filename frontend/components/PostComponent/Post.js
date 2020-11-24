import React from 'react';
import { Card } from 'react-native-elements';
import { Text } from 'react-native';

const Post = (props) => {
  return (
    <Card>
      <Card.Title>{props.author}</Card.Title>
      <Card.Divider />
      <Text style={{ textAlign: 'center' }}>{props.children}</Text>
    </Card>
  );
};

export default Post;
