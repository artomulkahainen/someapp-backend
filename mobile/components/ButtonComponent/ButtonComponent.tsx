import React from 'react';
import { StyleProp, ViewStyle } from 'react-native';
import { Button, IconNode } from 'react-native-elements';

export interface ButtonProps {
  icon?: IconNode;
  title: string;
  loading?: boolean;
  style?: StyleProp<ViewStyle>;
  onPress: () => void;
}

const ButtonComponent = ({ icon, title, loading, style, onPress }: ButtonProps) => (
  <Button
    buttonStyle={style}
    icon={icon}
    title={title}
    loading={loading}
    onPress={onPress}
    disabled={loading}
    style={{ marginLeft: 10 }}
  />
);

export default ButtonComponent;
