import {
  Alert,
  Button,
  NativeModules,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
import React, {useState} from 'react';

const {CounterService} = NativeModules;

type Props = {};

function SleepTimerScreen(props: Props) {
  const [initialCounter, setInitialCounter] = useState('');

  const startBackgroundService = () => {
    const counterValue = parseInt(initialCounter, 10);
    if (isNaN(counterValue)) {
      Alert.alert('Please enter a valid number');
      return;
    }

    // sent to CounterServiceModule
    CounterService.startService(counterValue);
  };
  const stopBackgroundService = () => {
    CounterService.stopService();
  };

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <TextInput
        style={{
          height: 40,
          borderColor: 'gray',
          borderWidth: 1,
          width: 200,
          marginBottom: 20,
          textAlign: 'center',
        }}
        placeholder="Enter initial counter value"
        keyboardType="numeric"
        value={initialCounter}
        onChangeText={setInitialCounter}
      />
      <Button title="Start Counter Service" onPress={startBackgroundService} />
      <Button title="Stop Counter Service" onPress={stopBackgroundService} />
    </View>
  );
}

export default SleepTimerScreen;

const styles = StyleSheet.create({});
