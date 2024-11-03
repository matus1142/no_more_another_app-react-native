/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useRef, useState} from 'react';
import {
  Button,
  DrawerLayoutAndroid,
  Text,
  StyleSheet,
  View,
  SafeAreaView,
} from 'react-native';

import GuideScreen from './app/guide_app/screens/GuideScreen';
import { NavigationContainer } from '@react-navigation/native';
import { createDrawerNavigator } from '@react-navigation/drawer';
import AboutScreen from './app/guide_app/screens/AboutScreen';
import SleepTimerScreen from './app/sleep_timer_app/screens/SleepTimerScreen';

const Drawer = createDrawerNavigator();

function App(): React.JSX.Element {
  return (

      <NavigationContainer>
        <Drawer.Navigator initialRouteName='Guide' >
          <Drawer.Screen name="Guide" component={GuideScreen} />
          <Drawer.Screen name="SleepTimer" component={SleepTimerScreen} />
        </Drawer.Navigator>
      </NavigationContainer>
  );
}
export default App;