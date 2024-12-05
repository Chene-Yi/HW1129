package com.example.lab12

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        executeBackgroundTask()
    }

    private fun executeBackgroundTask() {
        Thread {
            try {
                Thread.sleep(3000) // Delay for 3 seconds
                launchSecActivity()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun launchSecActivity() {
        val intent = Intent(this, SecActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK // Create a new instance of the Activity
        }
        startActivity(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Return START_NOT_STICKY to indicate that the Service should not restart after being killed
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null // No binding needed
}
