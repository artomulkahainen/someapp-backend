import React, { useState } from 'react';
import { View } from 'react-native';
import { set } from 'react-native-reanimated';
import TextArea from '../../components/TextArea/TextArea';

const NewPost = () => {
  const [comment, setComment] = useState('');

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <TextArea comment={comment} setComment={setComment} />
    </View>
  );
};

export default NewPost;
