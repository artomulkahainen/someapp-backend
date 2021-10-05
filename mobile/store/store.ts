import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import { userReducer } from './reducers/userReducer';
import { composeWithDevTools } from 'redux-devtools-extension';
import { persistCombineReducers, persistStore } from 'redux-persist';
import AsyncStorage from '@react-native-async-storage/async-storage';

const persistConfig = {
    key: 'persistedReducer',
    storage: AsyncStorage
};

const reducer = persistCombineReducers(persistConfig, {
    userReducer: userReducer
});

const store = createStore(reducer, composeWithDevTools(applyMiddleware(thunk)));
let persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default { store, persistor };
