import {
  Alert,
  Button,
  NativeEventEmitter,
  NativeModules,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
import React, {useEffect, useState} from 'react';

const {CounterService} = NativeModules;

type Props = {};

function SleepTimerScreen(props: Props) {
  const [initialCounter, setInitialCounter] = useState('');
  const [counterValue, setCounterValue] = useState(0);

  const startBackgroundService = () => {
    const initialValue = parseInt(initialCounter, 10);
    if (isNaN(initialValue)) {
      Alert.alert('Please enter a valid number');
      return;
    }

    // sent to CounterServiceModule
    CounterService.startService(initialValue);
  };
  const stopBackgroundService = () => {
    CounterService.stopService();
  };


  useEffect(() => {
    // Create an event emitter for the CounterService module
    const eventEmitter = new NativeEventEmitter(CounterService);

    // Listen for 'onCounterUpdate' events
    const counterListener = eventEmitter.addListener('onCounterUpdate', (data) => {
      setCounterValue(data.counter.toString());  // Update the state with the counter value
    });

    // Clean up listener on component unmount
    return () => {
      counterListener.remove();
    };
  }, []);
  
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      {/* Display the counter value */}
      <Text style={{ fontSize: 24 }}>Counter: {counterValue}</Text> 
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
