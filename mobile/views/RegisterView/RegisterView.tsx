import React, { useState } from 'react';
import { FormikValues } from 'formik';
import { saveNewUser } from '../../services/userService';
import FormComponent from '../../components/FormComponent/FormComponent';
import Snackbar from 'react-native-snackbar';
import { registerViewSchema } from '../../util/validationSchemas';
import { errorAlert } from '../../util/alertMessages';

export interface RegisterFormProps {
    toggleForm: () => void;
}

const RegisterView = ({ toggleForm }: RegisterFormProps) => {
    const [saving, setSaving] = useState<boolean>(false);

    const trySave = (values: FormikValues) => {
        if (values.password === values.passwordagain) {
            setSaving(true);
            saveNewUser({
                username: values.username,
                password: values.password
            })
                .then(() => {
                    toggleForm();
                })
                .catch((e: any) => {
                    Snackbar.show(errorAlert(`${e.message}, ${e.status}`));
                    console.log('error with saving new user');
                })
                .finally(() => setSaving(false));
        } else {
            Snackbar.show(errorAlert(`You have to write same password twice.`));
        }
    };

    return (
        <FormComponent
            submitButtonTitle="Register"
            inputPlaceholders={['Username', 'Password', 'Password again']}
            submitOperation={trySave}
            loading={saving}
            cancelAction={toggleForm}
            validationSchema={registerViewSchema}
        />
    );
};

export default RegisterView;
