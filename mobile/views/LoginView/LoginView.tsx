import React, { SetStateAction, useState } from 'react';
import { View, Text, Dimensions } from 'react-native';
import { login, SuccessfulLoginResponse } from '../../services/loginService';
import { red } from '../../util/styles/Colors';
import Snackbar from 'react-native-snackbar';
import ButtonComponent from '../../components/ButtonComponent/ButtonComponent';
import RegisterView from '../RegisterView/RegisterView';
import FormComponent from '../../components/FormComponent/FormComponent';
import { FormikValues } from 'formik';
import { styles } from '../../util/styles/BasicStyles';
import { errorAlert } from '../../util/alertMessages';

interface LoginProps {
    setLogged: (value: SetStateAction<boolean>) => void;
    saveToken: (token: string) => Promise<void>;
}

const LoginView = ({ setLogged, saveToken }: LoginProps) => {
    const [loading, setLoading] = useState(false);
    const [registerFormOpen, setRegisterFormOpen] = useState(false);

    const toggleRegisterForm = () => {
        setRegisterFormOpen(!registerFormOpen);
    };

    const tryLogin = async (values: FormikValues) => {
        setLoading(true);

        try {
            const res: SuccessfulLoginResponse = await login({ username: values.username, password: values.password });
            await saveToken(res.token);
            setLoading(false);
            setLogged(true);
        } catch (e: any) {
            // send snackbar message here instead of console log
            console.log(`${e.response.data.message}, ${e.response.data.status}`);
            Snackbar.show(errorAlert(`${e.response.data.message}, ${e.response.data.status}`));
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
                    <Text style={{ textAlign: 'center', fontSize: 30, color: red }}>GimmeVibe</Text>
                    <FormComponent
                        submitButtonTitle="Login"
                        inputPlaceholders={['Username', 'Password']}
                        submitOperation={tryLogin}
                        loading={loading}
                    />
                    <View style={styles.centerColumnView}>
                        <ButtonComponent
                            onPress={toggleRegisterForm}
                            title="Register new user"
                            style={{ marginTop: 50 }}
                        />
                    </View>
                </View>
            ) : (
                <RegisterView toggleForm={toggleRegisterForm} />
            )}
        </View>
    );
};

export default LoginView;
