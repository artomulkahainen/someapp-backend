import React, { useState } from 'react';
import { CheckBox, Input } from 'react-native-elements';
import { GestureResponderEvent, Text, View } from 'react-native';
import ButtonComponent from '../ButtonComponent/ButtonComponent';

interface InputWithButtonProps {
  inputPlaceholders: Array<string>;
  stateSetters: Array<React.Dispatch<React.SetStateAction<string>>>;
  inputType: string;
  loading?: boolean;
  buttonAction: (event: GestureResponderEvent) => void;
}

const InputWithButton = (props: InputWithButtonProps) => {
  const [showPassword, setShowPassword] = useState(false);

  const renderInputs = props.inputPlaceholders.map(
    (placeholder: string, index: number) => (
      <Input
        placeholder={placeholder}
        onChangeText={(text: string) => props.stateSetters[index](text)}
        key={index}
        secureTextEntry={showPassword ? false : placeholder === 'Password'}
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
        title={props.inputType}
        onPress={props.buttonAction}
        loading={props.loading}
      />
    </View>
  );
};

export default InputWithButton;
