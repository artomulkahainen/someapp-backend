import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import React, { useState } from 'react';
import { Text, View } from 'react-native';
import { Header, Icon } from 'react-native-elements';
import Feed from './views/Feed/Feed';
import Login from './views/Login/Login';
import NewPost from './views/NewPost/NewPost';
import Profile from './views/Profile/Profile';
import Settings from './views/SettingsScreen/Settings';

const Tab = createBottomTabNavigator();

const AppWrapper = () => {
  const [logged, setLogged] = useState(false);

  const navIconNames = {
    Feed: 'comment',
    Profile: 'person',
    NewPost: 'edit',
    Settings: 'settings',
    Logout: 'close'
  };

  const logout = () => {
    setLogged(false);
    return (
      <View>
        <Text>logged out</Text>
      </View>
    );
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
        <Tab.Screen name="Feed" component={Feed} />
        <Tab.Screen name="NewPost" component={NewPost} />
        <Tab.Screen name="Profile" component={Profile} />
        <Tab.Screen name="Settings" component={Settings} />
        <Tab.Screen name="Logout" component={logout} />
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
      <Login setLogged={() => setLogged(true)} />
    </View>
  );
};

export default AppWrapper;
