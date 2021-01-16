import React from 'react';
import { View, TextInput } from 'react-native';

const TextArea = ({ value, setValue }) => (
    <View>      
        <TextInput
            style={{ height: 200, borderColor: 'white', borderWidth: 1 }}
            onChangeText={setValue}
            value={value}
        />
    </View>
);

export default TextArea;