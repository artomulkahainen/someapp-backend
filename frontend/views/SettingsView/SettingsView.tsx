import React from 'react';
import { Text, View } from 'react-native';

interface SettingsViewProps {
  logout: () => Promise<void>;
}

const SettingsView = ({ logout }: SettingsViewProps) => {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Settings page!</Text>
    </View>
  );
};

export default SettingsView;
