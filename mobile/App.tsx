import React, { useEffect, useState } from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { Header, Icon } from 'react-native-elements';
import FeedView from './views/FeedView/FeedView';
import LoginView from './views/LoginView/LoginView';
import NewPostView from './views/NewPostView/NewPostView';
import ProfileView from './views/ProfileView/ProfileView';
import SettingsView from './views/SettingsView/SettingsView';
import { darkRed, red, white } from './util/styles/Colors';
import { saveToken, removeToken } from './util/storage/AsyncStorage';
import { ping, ServerStatus } from './services/pingService';
import useInterval from './util/hooks/useInterval';
import { Provider } from 'react-redux';
import store from './store/store';
import { PersistGate } from 'redux-persist/integration/react';
import { IIndexable } from './util/interfaces';

const Tab = createBottomTabNavigator();

const App = () => {
    const [logged, setLogged] = useState(false);
    const [serverOnline, setServerOnline] = useState(true);

    useEffect(() => {
        // check server status on app startup
        checkServerStatus();
    }, []);

    useInterval(() => {
        // check server status every 15sec
        checkServerStatus();
    }, 15000);

    const checkServerStatus = () => {
        ping()
            .then((res: ServerStatus) => {
                res.httpStatus === 'OK' ? setServerOnline(true) : setServerOnline(false);
                res.httpStatus === 'OK' && console.log(res);
            })
            .catch(() => setServerOnline(false));
    };

    const navIconNames: IIndexable = {
        Feed: 'comment',
        Profile: 'person',
        NewPost: 'edit',
        Settings: 'settings'
    };

    const logout = async () => {
        setLogged(false);
        await removeToken();
    };

    const Settings = () => {
        return <SettingsView logout={logout} />;
    };

    return (
        <Provider store={store.store}>
            <PersistGate loading={null} persistor={store.persistor}>
                {logged ? (
                    <NavigationContainer>
                        <Header
                            centerComponent={{
                                text: 'GimmeVibe',
                                style: { color: red }
                            }}
                            containerStyle={{
                                backgroundColor: white
                            }}
                        />
                        <Tab.Navigator
                            screenOptions={({ route }) => ({
                                tabBarIcon: ({ color }) => <Icon name={navIconNames[route.name]} color={color} />
                            })}
                            tabBarOptions={{
                                activeTintColor: red,
                                inactiveTintColor: darkRed,
                                tabStyle: { backgroundColor: white },
                                showLabel: false
                            }}
                        >
                            <Tab.Screen name="Feed" component={FeedView} />
                            <Tab.Screen name="NewPost" component={NewPostView} />
                            <Tab.Screen name="Profile" component={ProfileView} />
                            <Tab.Screen name="Settings" component={Settings} />
                        </Tab.Navigator>
                    </NavigationContainer>
                ) : (
                    <LoginView saveToken={saveToken} setLogged={() => setLogged(true)} />
                )}
            </PersistGate>
        </Provider>
    );
};

export default App;
