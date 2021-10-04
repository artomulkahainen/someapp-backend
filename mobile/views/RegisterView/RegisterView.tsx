import React, { useState } from 'react';
import { FormikValues } from 'formik';
import { saveNewUser } from '../../services/userService';
import FormComponent from '../../components/FormComponent/FormComponent';
import * as Yup from 'yup';
import Snackbar from 'react-native-snackbar';
import { red } from '../../util/styles/Colors';

export interface RegisterFormProps {
    toggleForm: () => void;
}

const RegisterView = ({ toggleForm }: RegisterFormProps) => {
    const [saving, setSaving] = useState<boolean>(false);

    const validationSchema = Yup.object().shape({
        username: Yup.string()
            .min(3, 'Username must be at least 3 characters long')
            .max(15, 'Username must be under 16 characters long')
            .required('Username is required'),
        password: Yup.string()
            .required('Password is required')
            .min(3, 'Password must be at least 3 characters long')
            .required('Password is required'),
        passwordagain: Yup.string()
            .required('Write password again')
            .min(3, 'Password must be at least 3 characters long')
    });

    const trySave = (values: FormikValues) => {
        if (values.password === values.passwordagain) {
            setSaving(true);
            saveNewUser({
                username: values.username,
                password: values.password
            })
                .then(() => {
                    setSaving(false);
                    toggleForm();
                })
                .catch((e: any) => {
                    setSaving(false);
                    Snackbar.show({
                        text: `${e.message}, ${e.status}`,
                        duration: Snackbar.LENGTH_SHORT,
                        backgroundColor: red
                    });
                    console.log('error with saving new user');
                });
        } else {
            Snackbar.show({
                text: `You have to write same password twice.`,
                duration: Snackbar.LENGTH_SHORT,
                backgroundColor: red
            });
        }
    };

    return (
        <FormComponent
            submitButtonTitle="Register"
            inputPlaceholders={['Username', 'Password', 'Password again']}
            submitOperation={trySave}
            loading={saving}
            cancelAction={toggleForm}
            validationSchema={validationSchema}
        />
    );
};

export default RegisterView;
