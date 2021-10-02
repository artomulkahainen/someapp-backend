import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { View, Text, Dimensions, StyleSheet } from 'react-native';
import InputWithButton from '../../components/InputWithButton/InputWithButton';
import { login, SuccessfulLoginResponse } from '../../services/loginService';
import { red, white } from '../../util/Colors';
import Snackbar from 'react-native-snackbar';
import ButtonComponent from '../../components/ButtonComponent/ButtonComponent';
import RegisterView from '../RegisterView/RegisterView';

interface LoginProps {
  setLogged: (value: SetStateAction<boolean>) => void;
  saveToken: (token: string) => Promise<void>;
}

const LoginView = ({ setLogged, saveToken }: LoginProps) => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [registerFormOpen, setRegisterFormOpen] = useState<boolean>(false);

  useEffect(() => {
    console.log(registerFormOpen);
  }, [registerFormOpen]);

  const toggleRegisterForm = () => {
    setRegisterFormOpen(!registerFormOpen);
  };

  const tryLogin = async () => {
    setLoading(true);

    try {
      const res: SuccessfulLoginResponse = await login({ username, password });
      await saveToken(res.token);
      setLoading(false);
      setLogged(true);
    } catch (e: any) {
      // send snackbar message here instead of console log
      console.log(`${e.response.data.message}, ${e.response.data.status}`);
      Snackbar.show({
        text: `${e.response.data.message}, ${e.response.data.status}`,
        duration: Snackbar.LENGTH_SHORT,
        backgroundColor: red
      });
      setLoading(false);
    }
  };

  return (
    <View
      style={{
        marginTop: Dimensions.get('window').height / 5
      }}
    >
      {!registerFormOpen ? (
        <View>
          <Text style={{ textAlign: 'center', fontSize: 30, color: 'red' }}>GimmeVibe</Text>
          <InputWithButton
            inputPlaceholders={['Username', 'Password']}
            stateSetters={[setUsername, setPassword]}
            inputTitle="Login"
            buttonAction={tryLogin}
            loading={loading}
            values={[username, password]}
          />
          <ButtonComponent onPress={toggleRegisterForm} title="Register new user" style={{ marginTop: 50 }} />
        </View>
      ) : (
        <RegisterView toggleForm={toggleRegisterForm} />
      )}
    </View>
  );
};

export default LoginView;
