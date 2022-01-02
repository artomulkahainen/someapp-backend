import Snackbar from 'react-native-snackbar';
import { red } from './styles/Colors';

export enum AlertLength {
    SHORT = Snackbar.LENGTH_SHORT,
    LONG = Snackbar.LENGTH_LONG
}

export const errorAlert = (text: string, duration: AlertLength = AlertLength.SHORT) => ({
    text,
    duration,
    backgroundColor: red
});

export const successAlert = (text: string, duration: AlertLength = AlertLength.SHORT) => ({
    text,
    duration,
    backgroundColor: 'green'
});
