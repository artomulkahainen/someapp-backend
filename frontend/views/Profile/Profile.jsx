import React from 'react';
import { Text, View } from 'react-native';
import ProfileCard from '../../components/ProfileCard/ProfileCard';
import Feed from '../Feed/Feed';

const profileExample = {
  username: 'Pertti',
  age: 19,
  favoriteMovie: 'The Mummy',
  info: 'Mä oon Pertti eli ekspertti.'
};

const Profile = () => {
  return (
    <View style={{ flex: 1 }}>
      <ProfileCard
        username={profileExample.username}
        info={profileExample.info}
        age={profileExample.age}
        favoriteMovie={profileExample.favoriteMovie}
      />
      <Text style={{ margin: 30, textAlign: 'center', fontSize: 20 }}>
        POSTS
      </Text>
      <Feed author={profileExample.username} />
    </View>
  );
};

export default Profile;
