package com.no_more_another_app

import android.content.Intent
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

// for sending events back to React Native
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments

// Define the module that will be used to communicate with React Native
class CounterServiceModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
        // Initialize the reactContext for use in sending events
        CounterServiceModule.reactContext  = reactContext
    }

    // Return the module name as it will be used in JavaScript
    override fun getName(): String {
        return "CounterService"
    }

    // Expose a method to start the background service
     // initialCounter is coming from JavaScript (client side)
    @ReactMethod
    fun startService(initialCounter: Int) {

        // Create an intent to start the CounterService
        val serviceIntent = Intent(reactContext , CounterService::class.java).apply {
            putExtra(CounterService.EXTRA_INITIAL_COUNTER, initialCounter)
        }
        reactContext.startService(serviceIntent)
    }
    

    // Expose a method to stop the background service
    @ReactMethod
    fun stopService() {
        // Create an intent to stop the CounterService
        val serviceIntent = Intent(reactContext, CounterService::class.java)
        reactContext.stopService(serviceIntent)
    }

    companion object {
        private lateinit var reactContext: ReactApplicationContext

        // Send counter update events to React Native
        fun sendCounterUpdate(counter: Int) {
            // Create a map to hold the counter value
            val params: WritableMap = Arguments.createMap()
            params.putInt("counter", counter) // Add the counter value to the map

            // Emit an event to React Native with the current counter value
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("onCounterUpdate", params) // Send event called 'onCounterUpdate'
        }
    }

}
