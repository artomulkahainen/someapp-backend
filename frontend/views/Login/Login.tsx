import React, { useState } from 'react';
import { View, Text, GestureResponderEvent, Dimensions } from 'react-native';
import InputWithButton from '../../components/InputWithButton/InputWithButton';
import { login } from '../../services/loginService';

interface LoginProps {
  setLogged: (event: GestureResponderEvent) => void;
}

const Login = (props: LoginProps) => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);

  const tryLogin = async () => {
    setLoading(true);
    const res = await login({ username, password });

    if (!res.error) {
      console.log(res.data);
    }

    setTimeout(() => {
      setLoading(false);
    }, 3000);
  };

  return (
    <View
      style={{
        marginTop: Dimensions.get('window').height / 5
      }}
    >
      <Text style={{ textAlign: 'center', fontSize: 30, color: 'red' }}>
        GimmeVibe
      </Text>
      <InputWithButton
        inputPlaceholders={['Username', 'Password']}
        stateSetters={[setUsername, setPassword]}
        inputType="Login"
        buttonAction={tryLogin}
        loading={loading}
      />
    </View>
  );
};

export default Login;
