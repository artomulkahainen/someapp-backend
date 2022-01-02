import React, { useState } from 'react';
import { CheckBox, Text } from 'react-native-elements';
import { TextInput, View } from 'react-native';
import ButtonComponent from '../ButtonComponent/ButtonComponent';
import { Formik, FormikErrors, FormikTouched, FormikValues } from 'formik';
import { lightGrey } from '../../util/styles/Colors';
import { styles } from '../../util/styles/BasicStyles';
import { AnyObjectSchema } from 'yup';
import { trimSpaces } from '../../util/textUtil';

interface FormComponentProps {
    inputPlaceholders: Array<string>;
    submitButtonTitle: string;
    loading?: boolean;
    cancelAction?: () => void;
    submitOperation: (values: FormikValues) => void;
    validationSchema?: AnyObjectSchema;
}

interface IFormikHandlers {
    handleChange: {
        (e: React.ChangeEvent<any>): void;
        <T = string | React.ChangeEvent<any>>(field: T): T extends React.ChangeEvent<any>
            ? void
            : (e: string | React.ChangeEvent<any>) => void;
    };
    handleBlur: {
        (e: React.FocusEvent<any>): void;
        <T = any>(fieldOrEvent: T): T extends string ? (e: any) => void : void;
    };
    handleSubmit: (e?: React.FormEvent<HTMLFormElement> | undefined) => void;
    values: FormikValues;
    errors: FormikErrors<FormikValues>;
    touched: FormikTouched<FormikValues>;
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

    const renderInputs = ({ handleChange, handleBlur, values, errors, touched }: IFormikHandlers) =>
        inputPlaceholders.map((placeholder: string, index: number) => {
            const phTrim = trimSpaces(placeholder);

            return (
                <View key={index}>
                    <TextInput
                        placeholder={placeholder}
                        onChangeText={handleChange(phTrim)}
                        onBlur={handleBlur(phTrim)}
                        key={index + 1}
                        secureTextEntry={showPassword ? false : phTrim.includes('password')}
                        value={values[phTrim]}
                        style={{ margin: 10, borderBottomWidth: 1, borderBottomColor: lightGrey }}
                    />
                    {errors[phTrim] && touched[phTrim] && (
                        <Text key={index + 2} style={styles.smallRedText}>
                            {errors[phTrim]}
                        </Text>
                    )}
                </View>
            );
        });

    const renderShowPasswordCheckbox = inputPlaceholders.some((placeholder: string) =>
        trimSpaces(placeholder).includes('password')
    );

    return (
        <Formik
            initialValues={inputPlaceholders.reduce(
                (inputsArray, input) => ({
                    ...inputsArray,
                    [trimSpaces(input)]: ''
                }),
                {}
            )}
            onSubmit={submitOperation}
            validationSchema={validationSchema}
        >
            {(handlers: IFormikHandlers) => (
                <View>
                    {renderInputs(handlers)}
                    {renderShowPasswordCheckbox && (
                        <View style={styles.centerColumnView}>
                            <CheckBox
                                title="Show password"
                                checked={showPassword}
                                onPress={() => setShowPassword(!showPassword)}
                            />
                        </View>
                    )}
                    <View style={styles.centerRowView}>
                        <ButtonComponent
                            title={submitButtonTitle}
                            onPress={handlers.handleSubmit}
                            loading={loading}
                            style={{ marginRight: !!cancelAction ? 30 : 0 }}
                            type="solid"
                        />
                        {!!cancelAction && (
                            <ButtonComponent title={'Cancel'} onPress={cancelAction} loading={loading} />
                        )}
                    </View>
                </View>
            )}
        </Formik>
    );
};

export default FormComponent;
