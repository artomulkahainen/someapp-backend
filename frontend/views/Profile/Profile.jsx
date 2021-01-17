import React from 'react';
import { Text, View } from 'react-native';
import ProfileCard from '../../components/ProfileCard/ProfileCard';

const posts = [
  {
    author: 'Pertti',
    post:
      'Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsumhallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulah',
  }
];

const Profile = () => {
  return (
    <View style={{ flex: 1 }}>
      <ProfileCard name='Kusti' info='Mä oon Kusti pojke' />
    </View>
  );
};

export default Profile;
