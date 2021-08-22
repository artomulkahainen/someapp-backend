import { useAsyncStorage } from '@react-native-async-storage/async-storage';
import AsyncStorageFunctions from './AsyncStorageFunctions';

const { getItem, setItem, removeItem } = useAsyncStorage('token');
export const { getToken, saveToken, removeToken } = AsyncStorageFunctions({
  getItem,
  setItem,
  removeItem
});
