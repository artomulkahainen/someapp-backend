import React from 'react';
import { View, Text } from 'react-native';
import { Button } from 'react-native-elements';

const Login = (props) => {
  return (
    <View>
      <Text>Login!</Text>
      <Button onPress={props.setLogged} title='Login' />
    </View>
  );
};

export default Login;
