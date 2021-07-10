import React from 'react';
import { Button } from 'react-native-elements';

export interface ButtonProps {
    icon?: any;
    title: string;
    loading?: any;
    style?: any;
}

const ButtonComponent = ({ icon, title, loading, style }: ButtonProps) => (
    <Button
        buttonStyle={{ backgroundColor: '#FF4500' }}
        icon={icon}
        title={title}
        loading={loading}
    />
);

export default ButtonComponent;