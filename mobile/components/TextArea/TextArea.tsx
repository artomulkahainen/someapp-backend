import React from 'react';
import { View, TextInput, Dimensions } from 'react-native';

let width = Dimensions.get('window').width;
let height = Dimensions.get('window').height;

interface TextAreaProps {
    value: any;
    setValue: any;
}

const TextArea = ({ value, setValue }: TextAreaProps) => (
    <View>
        <TextInput
            style={{
                height: height / 3,
                backgroundColor: 'white',
                borderWidth: 1,
                width: width / 2
            }}
            textAlign={'top'}
            onChangeText={setValue}
            value={value}
        />
    </View>
);

export default TextArea;
