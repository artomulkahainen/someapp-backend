import React from 'react';
import { Text, View } from 'react-native';
import ButtonComponent from '../../components/ButtonComponent/ButtonComponent';

interface SettingsViewProps {
    logout: () => Promise<void>;
}

const SettingsView = ({ logout }: SettingsViewProps) => {
    const tryLogout = () => {
        logout().catch((e: any) => console.log(e));
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Text>Settings page!</Text>
            <ButtonComponent title="Logout" onPress={tryLogout} />
        </View>
    );
};

export default SettingsView;
