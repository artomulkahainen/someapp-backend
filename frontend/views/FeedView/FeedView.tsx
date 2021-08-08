import React, { useCallback, useState } from 'react';
import { useFocusEffect } from '@react-navigation/native';
import PostListComponent from '../../components/PostListComponent/PostListComponent';
import { findMyUserDetails } from '../../services/userService';
import { ActivityIndicator, StyleSheet, View } from 'react-native';
import { getFriendsPosts } from '../../services/postService';

const FeedView = () => {
  const [loading, setLoading] = useState<boolean>(false);

  useFocusEffect(
    useCallback(() => {
      const fetchData = async () => {
        setLoading(true);
        const userDetailsRes = await findMyUserDetails();
        const postsRes = await getFriendsPosts();
        setLoading(false);
      };

      fetchData();
    }, [])
  );

  return (
    <View style={styles.container}>
      {loading ? (
        <ActivityIndicator size={100} color="#FF4500" />
      ) : (
        <PostListComponent />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center'
  }
});

export default FeedView;
