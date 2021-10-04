import React, { useState } from 'react';
import { CheckBox, Text } from 'react-native-elements';
import { TextInput, View } from 'react-native';
import ButtonComponent from '../ButtonComponent/ButtonComponent';
import { Formik, FormikValues } from 'formik';
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

const FormComponent = ({
    submitOperation,
    inputPlaceholders,
    submitButtonTitle,
    loading,
    cancelAction,
    validationSchema
}: FormComponentProps) => {
    const [showPassword, setShowPassword] = useState(false);

    const renderInputs = (handleChange: any, handleBlur: any, values: FormikValues, errors: any, touched: any) =>
        inputPlaceholders.map((placeholder: string, index: number) => {
            const phTrim = trimSpaces(placeholder);

            return (
                <View>
                    <TextInput
                        placeholder={placeholder}
                        onChangeText={handleChange(phTrim)}
                        onBlur={handleBlur(phTrim)}
                        key={index}
                        secureTextEntry={showPassword ? false : phTrim.includes('password')}
                        value={values[phTrim]}
                        style={{ margin: 10, borderBottomWidth: 1, borderBottomColor: lightGrey }}
                    />
                    {errors[phTrim] && touched[phTrim] ? (
                        <Text style={styles.smallRedText}>{errors[phTrim]}</Text>
                    ) : null}
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
            {({ handleChange, handleBlur, handleSubmit, values, errors, touched }) => (
                <View>
                    {renderInputs(handleChange, handleBlur, values, errors, touched)}
                    {renderShowPasswordCheckbox && (
                        <View style={styles.centerColumnView}>
                            <CheckBox
                                title="Show password"
                                checked={showPassword}
                                onPress={() => setShowPassword(!showPassword)}
                            />
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
