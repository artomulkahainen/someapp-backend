import React, { useState } from 'react';
import { CheckBox } from 'react-native-elements';
import { TextInput, View } from 'react-native';
import ButtonComponent from '../ButtonComponent/ButtonComponent';
import { Formik, FormikValues } from 'formik';
import { lightGrey } from '../../util/styles/Colors';
import { styles } from '../../util/styles/BasicStyles';
import { AnyObjectSchema } from 'yup';

interface FormComponentProps {
  inputPlaceholders: Array<string>;
  submitButtonTitle: string;
  loading?: boolean;
  cancelAction?: () => void;
  submitOperation: (values: FormikValues) => void;
  validationSchema?: AnyObjectSchema;
}

const FormComponent = ({
  submitOperation,
  inputPlaceholders,
  submitButtonTitle,
  loading,
  cancelAction,
  validationSchema
}: FormComponentProps) => {
  const [showPassword, setShowPassword] = useState(false);

  const renderInputs = (handleChange: any, handleBlur: any, values: FormikValues) =>
    inputPlaceholders.map((placeholder: string, index: number) => (
      <TextInput
        placeholder={placeholder}
        onChangeText={handleChange(placeholder.toLowerCase().replace(' ', ''))}
        onBlur={handleBlur(placeholder.toLowerCase().replace(' ', ''))}
        key={index}
        secureTextEntry={showPassword ? false : placeholder.toLowerCase().includes('password')}
        value={values[placeholder.toLowerCase().replace(' ', '')]}
        style={{ margin: 10, borderBottomWidth: 1, borderBottomColor: lightGrey }}
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
          [input.toLowerCase().replace(' ', '')]: ''
        }),
        {}
      )}
      onSubmit={submitOperation}
      validationSchema={validationSchema}
    >
      {({ handleChange, handleBlur, handleSubmit, values }) => (
        <View>
          {renderInputs(handleChange, handleBlur, values)}
          {renderShowPasswordCheckbox && (
            <View style={styles.centerColumnView}>
              <CheckBox title="Show password" checked={showPassword} onPress={() => setShowPassword(!showPassword)} />
            </View>
          )}
          <View
            style={{
              display: 'flex',
              flexDirection: 'row',
              justifyContent: 'center',
              alignItems: 'center',
              marginTop: 20
            }}
          >
            <ButtonComponent
              title={submitButtonTitle}
              onPress={handleSubmit}
              loading={loading}
              style={{ marginRight: !!cancelAction ? 30 : 0 }}
            />
            {!!cancelAction && <ButtonComponent title={'Cancel'} onPress={cancelAction} loading={loading} />}
          </View>
        </View>
      )}
    </Formik>
  );
};

export default FormComponent;
