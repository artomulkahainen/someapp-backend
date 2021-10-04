import React from 'react';
import { StyleProp, ViewStyle } from 'react-native';
import { Button, IconNode } from 'react-native-elements';
import { red } from '../../util/styles/Colors';

export interface ButtonProps {
    icon?: IconNode;
    title: string;
    loading?: boolean;
    style?: StyleProp<ViewStyle>;
    onPress: () => void;
    type?: 'clear' | 'solid' | 'outline';
}

const ButtonComponent = ({ icon, title, loading, style, onPress, type = 'clear' }: ButtonProps) => (
    <Button
        buttonStyle={style}
        icon={icon}
        title={title}
        loading={loading}
        onPress={onPress}
        disabled={loading}
        style={{ marginLeft: 10 }}
        type={type}
    />
);

export default ButtonComponent;
