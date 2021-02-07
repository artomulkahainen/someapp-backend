import React from 'react';
import { Text } from 'react-native';
import { Card } from 'react-native-elements';

const ProfileCard = ({ username, info, age, favoriteMovie }) => (
  <Card>
    <Card.Title>{username}</Card.Title>
    <Card.Divider />
    <Text style={{ margin: 2 }}>{`Age: ${age}`}</Text>
    <Text style={{ marginBottom: 20 }}>{info}</Text>
    <Card.Divider />
    <Text
      style={{ marginBottom: 20 }}
    >{`Favorite movie: ${favoriteMovie}`}</Text>
  </Card>
);

export default ProfileCard;
