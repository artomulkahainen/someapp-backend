import React from 'react';
import { FlatList, View, StyleSheet } from 'react-native';
import Post from '../../components/PostComponent/Post';

const Feed = () => {
  const styles = StyleSheet.create({
    separator: {
      height: 10,
    },
  });

  let posts = [
    {
      author: 'Pertti',
      post:
        'Lorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLoremipsum hallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsumhallulahLorem ipsum hallulahLorem ipsum hallulahLorem ipsum hallulah',
    },
    {
      author: 'Kalle',
      post: 'Elämä on',
    },
    {
      author: 'Kerttu',
      post: 'Joskus vain',
    },
    {
      author: 'Kusti',
      post:
        'Sieniä kasvaa sateellaSieniä kasvaa sateellaSieniä kasvaa sateellaSieniä kasvaa sateellaSieniä kasvaa sateellaSieniä kasvaa sateellaSieniä kasvaa sateellaSieniä kasvaa sateella',
    },
    {
      author: 'Kerttu',
      post:
        'Joskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vainJoskus vain',
    },
  ];

  const renderPost = ({ item }) => (
    <Post author={item.author} post={item.post} />
  );

  return <FlatList data={posts} renderItem={renderPost} />;
};

export default Feed;
