import React, { useState } from 'react';
import { FormikValues } from 'formik';
import { saveNewUser } from '../../services/userService';
import FormComponent from '../../components/FormComponent/FormComponent';

export interface RegisterFormProps {
  toggleForm: () => void;
}

const RegisterView = ({ toggleForm }: RegisterFormProps) => {
  const [saving, setSaving] = useState<boolean>(false);

  const trySave = (values: FormikValues) => {
    console.log(values);
    setSaving(true);
    saveNewUser({
      username: values.username,
      password: values.password
    })
      .then(() => {
        setSaving(false);
        toggleForm();
      })
      .catch(() => {
        setSaving(false);
        console.log('error with saving new user');
      });
  };

  return (
    <FormComponent
      submitButtonTitle="Register"
      inputPlaceholders={['Username', 'Password', 'Password again']}
      submitOperation={trySave}
      loading={saving}
      cancelAction={toggleForm}
    />
  );
};

export default RegisterView;
