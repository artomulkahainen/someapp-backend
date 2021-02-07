import React, { useState } from 'react';
import { Text, View } from 'react-native';
import { Header } from 'react-native-elements';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Icon } from 'react-native-elements';
import Profile from './views/Profile/Profile';
import Feed from './views/Feed/Feed';
import NewPost from './views/NewPost/NewPost';
import Settings from './views/SettingsScreen/Settings';
import Login from './views/Login/Login';
import Posts from './util/posts';

const Tab = createBottomTabNavigator();

const App = () => {
  const [logged, setLogged] = useState(true);

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
    <Login setLogged={() => setLogged(true)} />
  );
};

export default App;
