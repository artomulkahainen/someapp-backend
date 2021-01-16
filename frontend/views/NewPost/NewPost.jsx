import React, { useState } from 'react';
import { View } from 'react-native';
import TextArea from '../../components/TextArea/TextArea';
import ButtonComponent from '../../components/ButtonComponent/ButtonComponent';

const NewPost = () => {
  const [comment, setComment] = useState('');

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <TextArea value={comment} setValue={setComment} />
      <ButtonComponent title='Press me'/>
    </View>
  );
};

export default NewPost;
