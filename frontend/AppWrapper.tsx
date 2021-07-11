import React, { useState } from 'react';
import { useAsyncStorage } from '@react-native-async-storage/async-storage';
import AsyncStorageFunctions from './util/storage/AsyncStorageFunctions';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { View } from 'react-native';
import { Header, Icon } from 'react-native-elements';
import FeedView from './views/FeedView/FeedView';
import LoginView from './views/LoginView/LoginView';
import NewPostView from './views/NewPostView/NewPostView';
import ProfileView from './views/ProfileView/ProfileView';
import SettingsView from './views/SettingsView/SettingsView';

const Tab = createBottomTabNavigator();

const AppWrapper = () => {
  const { getItem, setItem, removeItem } = useAsyncStorage('token');
  const { getToken, saveToken, removeToken } = AsyncStorageFunctions({
    getItem,
    setItem,
    removeItem
  });

  const [logged, setLogged] = useState(false);

  const navIconNames = {
    Feed: 'comment',
    Profile: 'person',
    NewPost: 'edit',
    Settings: 'settings'
  };

  const logout = async () => {
    setLogged(false);
    await removeToken();
  };

  const Settings = (): JSX.Element => {
    return <SettingsView logout={logout} />;
  };

  return logged ? (
    <NavigationContainer>
      <Header
        centerComponent={{
          text: 'GimmeVibe',
          style: { color: '#FF4500' }
        }}
        containerStyle={{
          backgroundColor: 'white'
        }}
      />
      <Tab.Navigator
        screenOptions={({ route }) => ({
          tabBarIcon: ({ focused, color, size }) => (
            // @ts-ignore: Got to fix at some point
            <Icon name={navIconNames[route.name]} color={color} />
          )
        })}
        tabBarOptions={{
          activeTintColor: '#FF4500',
          inactiveTintColor: 'black',
          tabStyle: { backgroundColor: 'white' },
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
    <View
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignContent: 'center'
      }}
    >
      <LoginView saveToken={saveToken} setLogged={() => setLogged(true)} />
    </View>
  );
};

export default AppWrapper;
