import React from 'react';
import { Text } from 'react-native';
import { Card } from 'react-native-elements';

const ProfileCard = ({name, imgSource, info}) => (
    <Card>
        <Card.Title>{name}</Card.Title>
        <Card.Divider />
        {/*<Card.Image source={require('../../assets/favicon.png')} />*/}
        <Text>{info}</Text>       
    </Card>
);

export default ProfileCard;