package com.no_more_another_app

import android.content.Intent
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class CounterServiceModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    // Initialize the React application context
    private val context = reactContext

    // Return the module name as it will be used in JavaScript
    override fun getName(): String {
        return "CounterService"
    }

    // Expose a method to start the background service
     // initialCounter is coming from JavaScript (client side)
    @ReactMethod
    fun startService(initialCounter: Int) {

        // Create an intent to start the CounterService
        val serviceIntent = Intent(context, CounterService::class.java).apply {
            putExtra(CounterService.EXTRA_INITIAL_COUNTER, initialCounter)
        }
        context.startService(serviceIntent)
    }

    // Expose a method to stop the background service
    @ReactMethod
    fun stopService() {
        // Create an intent to stop the CounterService
        val serviceIntent = Intent(context, CounterService::class.java)
        context.stopService(serviceIntent)
    }

}
