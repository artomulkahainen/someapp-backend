import * as Yup from 'yup';

export const registerViewSchema = Yup.object().shape({
    username: Yup.string()
        .min(3, 'Username must be at least 3 characters long')
        .max(15, 'Username must be under 16 characters long')
        .required('Username is required'),
    password: Yup.string()
        .required('Password is required')
        .min(3, 'Password must be at least 3 characters long')
        .required('Password is required'),
    passwordagain: Yup.string().required('Write password again').min(3, 'Password must be at least 3 characters long')
});
