import React from 'react';
import { View, TextInput } from 'react-native';

const TextArea = ({ comment, setComment }) => (
    <View>      
        <TextInput
            style={{ height: 200, borderColor: 'gray', borderWidth: 1 }}
            onChangeText={setComment}
            value={comment}
        />
    </View>
);

export default TextArea;