import React from 'react';
import { Text, View } from 'react-native';
import PostListComponent from '../../components/PostListComponent/PostListComponent';
import ProfileCard from '../../components/ProfileCard/ProfileCard';

const profileExample = {
  username: 'Pertti',
  age: 19,
  favoriteMovie: 'The Mummy',
  info: 'Mä oon Pertti eli ekspertti.'
};

const ProfileView = () => {
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
      <PostListComponent author={profileExample.username} />
    </View>
  );
};

export default ProfileView;
