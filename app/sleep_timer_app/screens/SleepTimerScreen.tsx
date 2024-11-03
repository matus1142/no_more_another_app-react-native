import {Button, NativeModules, StyleSheet, Text, View} from 'react-native';
import React from 'react';

const {CounterService} = NativeModules;

type Props = {};

function SleepTimerScreen(props: Props) {
  const startBackgroundService = () => {
    CounterService.startService();
  };
  const stopBackgroundService = () => {
    CounterService.stopService();
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
