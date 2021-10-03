import { StyleSheet } from 'react-native';
import { errorRed } from './Colors';

export const styles = StyleSheet.create({
  centerColumnView: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  centerRowView: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20
  },
  smallRedText: {
    color: errorRed,
    marginLeft: 10
  }
});
