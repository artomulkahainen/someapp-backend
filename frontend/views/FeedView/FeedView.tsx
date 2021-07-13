import React, { useCallback, useState } from 'react';
import { useFocusEffect } from '@react-navigation/native';
import PostFeedComponent from '../../components/PostFeedComponent/PostFeedComponent';
import { findMyUserDetails } from '../../services/userService';
import { View } from 'react-native';

interface FeedProps {
  author?: string;
}

interface PostProps {
  item: any;
}

const FeedView = ({ author }: FeedProps) => {
  const [loading, setLoading] = useState<boolean>(false);

  useFocusEffect(
    useCallback(() => {
      let isActive = true;

      const fetchData = async () => {
        setLoading(true);
        const res = await findMyUserDetails();
        setLoading(false);
      };

      fetchData();

      return () => {
        isActive = false;
      };
    }, [])
  );

  return loading ? <View /> : <PostFeedComponent />;
};

export default FeedView;
