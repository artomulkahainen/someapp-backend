import React, { Dispatch, SetStateAction, useState } from 'react';
import { View, Text, Dimensions } from 'react-native';
import InputWithButton from '../../components/InputWithButton/InputWithButton';
import { login, SuccessfulLoginResponse } from '../../services/loginService';
import { red, white } from '../../util/Colors';
import Snackbar from 'react-native-snackbar';
import ButtonComponent from '../../components/ButtonComponent/ButtonComponent';

interface LoginProps {
  setLogged: (value: SetStateAction<boolean>) => void;
  saveToken: (token: string) => Promise<void>;
  toggleRegisterForm: Dispatch<React.SetStateAction<boolean>>;
}

const LoginView = ({
  setLogged,
  saveToken,
  toggleRegisterForm
}: LoginProps) => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);

  const handleFormOpen = () => {
    toggleRegisterForm(true);
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
      <ButtonComponent onPress={handleFormOpen} title="Register new user" />
    </View>
  );
};

export default LoginView;
