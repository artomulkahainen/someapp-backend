import React from 'react';
import { View, Text } from 'react-native';
import { Button } from 'react-native-elements';

interface LoginProps {
  setLogged: any;
}

const Login = (props: LoginProps) => {
  return (
    <View>
      <Text>Login!</Text>
      <Button onPress={props.setLogged} title='Login' />
    </View>
  );
};

export default Login;
