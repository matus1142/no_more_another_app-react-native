package com.no_more_another_app


import android.content.Intent
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

// Define a module to expose background service start/stop functionality to React Native
class BackgroundServiceModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    // Initialize the React application context
    private val context = reactContext

    // Return the module name as it will be used in JavaScript
    override fun getName(): String {
        return "BackgroundService"
    }

    // Expose a method to start the background service
    @ReactMethod
    fun startService() {
        // Create an intent for the background service
        val serviceIntent = Intent(context, MyBackgroundService::class.java)
        // Start the service using the context
        context.startService(serviceIntent)
    }

    // Expose a method to stop the background service
    @ReactMethod
    fun stopService() {
        // Create an intent for the background service
        val serviceIntent = Intent(context, MyBackgroundService::class.java)
        // Stop the service using the context
        context.stopService(serviceIntent)
    }
}
