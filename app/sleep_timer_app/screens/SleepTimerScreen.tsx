import {Button, NativeModules, StyleSheet, Text, View} from 'react-native';
import React from 'react';

const {BackgroundService} = NativeModules;

type Props = {};

function SleepTimerScreen(props: Props) {
  const startBackgroundService = () => {
    BackgroundService.startService();
  };
  const stopBackgroundService = () => {
    BackgroundService.stopService();
  };

  return (
    <View>
      <Button title="Start Background Service" onPress={startBackgroundService} />
      <Button title="Stop Background Service" onPress={stopBackgroundService} />
    </View>
  );
}

export default SleepTimerScreen;

const styles = StyleSheet.create({});
