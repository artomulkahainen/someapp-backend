import React from 'react';
import { Button } from 'react-native-elements';

const ButtonComponent = ({ icon, title, loading, style }) => (
    <Button
        buttonStyle={{ backgroundColor: '#FF4500' }}
        icon={icon}
        title={title}
        loading={loading}
    />
);

export default ButtonComponent;