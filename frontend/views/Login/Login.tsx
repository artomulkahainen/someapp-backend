import React, { useState } from 'react';
import { View, Text, GestureResponderEvent, Dimensions } from 'react-native';
import InputWithButton from '../../components/InputWithButton/InputWithButton';

interface LoginProps {
  setLogged: (event: GestureResponderEvent) => void;
}

const Login = (props: LoginProps) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const tryLogin = async () => {
    setLoading(true);
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
