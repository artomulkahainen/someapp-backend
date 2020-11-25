import React, { useState } from 'react';
import { Text, View } from 'react-native';
import { Header } from 'react-native-elements';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Icon } from 'react-native-elements';
import Profile from './containers/Profile/Profile';
import Feed from './containers/Feed/Feed';
import NewPost from './components/NewPost/NewPost';
import Settings from './containers/SettingsScreen/Settings';
import Login from './components/Login/Login';

const Tab = createBottomTabNavigator();

export default function App() {
  const [logged, setLogged] = useState(true);

  const logout = () => {
    setLogged(false);
    return (
      <View>
        <Text>logged out</Text>
      </View>
    );
  };

  const view = logged ? (
    <NavigationContainer>
      <Header
        centerComponent={{
          text: 'GimmeVibe',
          style: { color: '#fff' },
        }}
        containerStyle={{
          backgroundColor: '#3D6DCC',
        }}
      />
      <Tab.Navigator
        screenOptions={({ route }) => ({
          tabBarIcon: ({ focused, color, size }) => {
            let iconName;

            if (route.name === 'Feed') {
              iconName = 'comment';
            } else if (route.name === 'Profile') {
              iconName = 'person';
            } else if (route.name === 'New Post') {
              iconName = 'edit';
            } else if (route.name === 'Settings') {
              iconName = 'settings';
            } else if (route.name === 'Logout') {
              iconName = 'close';
            }

            return <Icon name={iconName} color={color} />;
          },
        })}
        tabBarOptions={{
          activeTintColor: 'white',
          inactiveTintColor: 'black',
          tabStyle: { backgroundColor: '#3D6DCC' },
          showLabel: false,
        }}>
        <Tab.Screen name='Feed' component={Feed} />
        <Tab.Screen name='New Post' component={NewPost} />
        <Tab.Screen name='Profile' component={Profile} />
        <Tab.Screen name='Settings' component={Settings} />
        <Tab.Screen name='Logout' component={logout} />
      </Tab.Navigator>
    </NavigationContainer>
  ) : (
    <Login setLogged={() => setLogged(true)}></Login>
  );

  return view;
}
