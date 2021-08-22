import React, { useEffect, useState } from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { View } from 'react-native';
import { Header, Icon } from 'react-native-elements';
import FeedView from './views/FeedView/FeedView';
import LoginView from './views/LoginView/LoginView';
import NewPostView from './views/NewPostView/NewPostView';
import ProfileView from './views/ProfileView/ProfileView';
import SettingsView from './views/SettingsView/SettingsView';
import { black, darkRed, red, white } from './util/Colors';
import { saveToken, removeToken } from './util/storage/AsyncStorage';
import { ping, ServerStatus } from './services/pingService';
import useInterval from './util/hooks/useInterval';
import RegisterForm from './views/RegisterView/RegisterForm';

const Tab = createBottomTabNavigator();

const App = () => {
  const [logged, setLogged] = useState(false);
  const [registerFormOpen, setRegisterFormOpen] = useState<boolean>(false);
  const [serverOnline, setServerOnline] = useState<boolean>(true);

  useEffect(() => {
    // check server status on app startup
    checkServerStatus();
  }, []);

  useInterval(() => {
    // check server status every 15sec
    checkServerStatus();
  }, 15000);

  const checkServerStatus = () => {
    ping().then((res: ServerStatus) => {
      res.httpStatus === 'OK' ? setServerOnline(true) : setServerOnline(false);
      res.httpStatus === 'OK' && console.log(res);
    });
  };

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
          style: { color: red }
        }}
        containerStyle={{
          backgroundColor: white
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
    <View
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignContent: 'center'
      }}
    >
      <LoginView
        saveToken={saveToken}
        setLogged={() => setLogged(true)}
        toggleRegisterForm={setRegisterFormOpen}
      />
      <RegisterForm
        toggleForm={setRegisterFormOpen}
        formOpen={registerFormOpen}
      />
    </View>
  );
};

export default App;
