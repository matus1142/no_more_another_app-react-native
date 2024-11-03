package com.no_more_another_app

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

// Define a background service class inheriting from Service
class MyBackgroundService : Service() {

    // Create a Handler for scheduling tasks in the background thread
    private lateinit var handler: Handler
    
    // Define a Runnable task that repeats actions at intervals
    private lateinit var runnable: Runnable

    override fun onCreate() {
        super.onCreate()
        
        // Initialize the handler
        handler = Handler()

        // Define the task to be repeated every few seconds
        runnable = Runnable {
            // Display a toast message as an example of a background task
            Toast.makeText(this@MyBackgroundService, "Background task running", Toast.LENGTH_SHORT).show()
            
            // Schedule the task to repeat every 5 seconds
            handler.postDelayed(runnable, 5000)
        }
    }

    // Called when the service starts
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the task by posting it to the handler
        handler.post(runnable)
        return START_STICKY  // Restart service if it's terminated
    }

    // Called when the service is stopped or destroyed
    override fun onDestroy() {
        super.onDestroy()
        // Remove the runnable to stop background execution
        handler.removeCallbacks(runnable)
    }

    // Required method for Service class, but not used here as no binding is needed
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
