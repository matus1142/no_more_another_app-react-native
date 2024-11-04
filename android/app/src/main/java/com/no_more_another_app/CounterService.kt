package com.no_more_another_app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent;
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Build
import androidx.core.app.NotificationCompat

class CounterService : Service() {

    private var counter = 0

    // Create a Handler for scheduling tasks in the background thread
    private lateinit var handler: Handler
    
    // Define a Runnable task that repeats actions at intervals
    private lateinit var runnable: Runnable

    // Inside CounterService.kt, add EXTRA_INITIAL_COUNTER as a companion object. 
    // This makes it accessible as a constant key for retrieving the initial counter value from the Intent.
    companion object {
        const val EXTRA_INITIAL_COUNTER = "initial_counter"  // Key for the initial counter value
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize handler for scheduling the counter update
        handler = Handler()

        // Define the task to be repeated at intervals
        runnable = Runnable {

           // Check if the counter has reached the limit
            if (counter > 30) {
                stopSelf()  // Stop the service if the counter exceeds 30
                return@Runnable  // Exit the runnable to avoid further execution
            }

            // Update the notification with the current count
            showNotification(counter) 

            // Send update to React Native (sent to CounterServiceModule)
            CounterServiceModule.sendCounterUpdate(counter)

            // Increment the counter
            counter++

            // Repeat every second
            handler.postDelayed(runnable, 1000)
        }
    }

    // Called when the service is started
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Get the initial counter value from the intent if provided
        counter = intent?.getIntExtra(EXTRA_INITIAL_COUNTER, 0) ?: 0  // Set default to 0 if not provided

        // Start the task by posting it to the handler
        handler.post(runnable)
        return START_STICKY  // Ensures the service is restarted if terminated
    }

    // Called when the service is destroyed
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)  // Stop updating when the service is destroyed
    }

    // Required method for Service class, but not used here as no binding is needed
    override fun onBind(intent: Intent?): IBinder? {
        return null  // Binding not needed for this service
    }

    // Update the notification with the current counter value
    private fun showNotification(counter: Int) {
        val channelId = "counter_service_channel"
        val channelName = "Counter Service"

        // For Android 8.0 and above, create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // Create an Intent to open the main activity when the notification is tapped
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        
        // Wrap the Intent in a PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent, 
            PendingIntent.FLAG_MUTABLE
        )

        // Build the notification
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Sleep Timer") // Title of the notification
            .setContentText("Count: $counter seconds") // Text to display the current counter value
            .setSmallIcon(R.drawable.ic_launcher) // Ensure this icon exists in drawable folder
            .setContentIntent(pendingIntent)  // Set the pending intent for click action
            .setPriority(NotificationCompat.PRIORITY_LOW) // Set priority for visibility on lock screen
            .setOngoing(true)  // Keeps the notification persistent
            .build()

        // Display the notification
        startForeground(1, notification)  // Start service in the foreground with the notification
    }

}
