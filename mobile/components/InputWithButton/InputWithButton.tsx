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
import { FormikValues } from 'formik';

interface InputWithButtonProps {
  inputPlaceholders: Array<string>;
  stateSetters?: Array<React.Dispatch<React.SetStateAction<string>>>;
  inputTitle: string;
  loading?: boolean;
  handleChange?: (e: string | React.ChangeEvent<any>) => void;
  handleBlur?: (e: NativeSyntheticEvent<TextInputFocusEventData>) => void;
  buttonAction: (
    e: GestureResponderEvent | React.FormEvent<HTMLFormElement> | undefined
  ) => void;
  values: any;
}

const InputWithButton = (props: InputWithButtonProps) => {
  const [showPassword, setShowPassword] = useState(false);

  const renderInputs = props.inputPlaceholders.map(
    (placeholder: string, index: number) => (
      <Input
        placeholder={placeholder}
        onChangeText={(text: string) =>
          !!props.stateSetters
            ? props.stateSetters[index](text)
            : props.handleChange!(placeholder)
        }
        /*onBlur={
          !props.stateSetters ? undefined : props.handleBlur!(placeholder)
        }*/
        key={index}
        secureTextEntry={
          showPassword ? false : placeholder.toLowerCase().includes('password')
        }
        value={
          !props.stateSetters ? props.values[placeholder] : props.values[index]
        }
      />
    )
  );

  const renderShowPasswordCheckbox = props.inputPlaceholders.some(
    (placeholder: string) => placeholder === 'Password'
  );

  return (
    <View
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
      }}
    >
      {renderInputs}
      {renderShowPasswordCheckbox && (
        <CheckBox
          title="Show password"
          checked={showPassword}
          onPress={() => setShowPassword(!showPassword)}
        />
      )}
      <ButtonComponent
        title={props.inputTitle}
        onPress={props.buttonAction}
        loading={props.loading}
      />
    </View>
  );
};

export default InputWithButton;
