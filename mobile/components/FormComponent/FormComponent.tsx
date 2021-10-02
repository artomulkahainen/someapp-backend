import React, { useState } from 'react';
import { CheckBox, Input } from 'react-native-elements';
import {
  GestureResponderEvent,
  NativeSyntheticEvent,
  Text,
  TextInput,
  TextInputFocusEventData,
  View
} from 'react-native';
import ButtonComponent from '../ButtonComponent/ButtonComponent';
import { Formik, FormikValues } from 'formik';

interface FormComponentProps {
  inputPlaceholders: Array<string>;
  submitButtonTitle: string;
  loading?: boolean;
  cancelAction?: () => void;
  submitOperation: (values: FormikValues) => void;
}

const FormComponent = ({
  submitOperation,
  inputPlaceholders,
  submitButtonTitle,
  loading,
  cancelAction
}: FormComponentProps) => {
  const [showPassword, setShowPassword] = useState(false);

  const renderInputs = (
    handleChange: (e: string | React.ChangeEvent<any>) => void,
    handleBlur: (e: any) => void,
    values: FormikValues
  ) =>
    inputPlaceholders.map((placeholder: string, index: number) => (
      <Input
        placeholder={placeholder}
        onChangeText={(_: string) => handleChange(placeholder.toLowerCase().trim())}
        onBlur={handleBlur(placeholder.toLowerCase().trim())}
        key={index}
        secureTextEntry={showPassword ? false : placeholder.toLowerCase().includes('password')}
        value={values[placeholder.toLowerCase().trim()]}
      />
    ));

  const renderShowPasswordCheckbox = inputPlaceholders.some((placeholder: string) =>
    placeholder.toLowerCase().includes('password')
  );

  return (
    <Formik
      initialValues={inputPlaceholders.reduce(
        (inputsArray, input) => ({
          ...inputsArray,
          [input.toLowerCase().trim()]: ''
        }),
        {}
      )}
      onSubmit={submitOperation}
    >
      {({ handleChange, handleBlur, handleSubmit, values }) => (
        <View>
          {/*<TextInput
              onChangeText={handleChange('email')}
              onBlur={handleBlur('email')}
              value={values.email}
            />*/}

          {renderInputs(handleChange, handleBlur, values)}
          {renderShowPasswordCheckbox && (
            <CheckBox title="Show password" checked={showPassword} onPress={() => setShowPassword(!showPassword)} />
          )}
          <ButtonComponent title={submitButtonTitle} onPress={handleSubmit} loading={loading} />
          {!!cancelAction && <ButtonComponent title={'Cancel'} onPress={cancelAction} loading={loading} />}
        </View>
      )}
    </Formik>
  );
};

export default FormComponent;
