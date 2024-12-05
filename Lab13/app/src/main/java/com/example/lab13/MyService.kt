package com.example.lab13

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Extract the channel from the intent
        channel = intent?.getStringExtra("channel").orEmpty()

        // Send the initial broadcast message based on the channel
        sendBroadcastMessage(
            when (channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )

        // Interrupt and clean up the previous thread if running
        if (::thread.isInitialized && thread.isAlive) {
            thread.interrupt()
        }

        // Create and start a new thread for delayed broadcast
        thread = Thread {
            try {
                Thread.sleep(3000) // Delay for 3 seconds
                sendBroadcastMessage(
                    when (channel) {
                        "music" -> "即將播放本月TOP10音樂"
                        "new" -> "即將為您提供獨家新聞"
                        "sport" -> "即將播報本週NBA賽事"
                        else -> "頻道錯誤"
                    }
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        thread.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    // Helper method to send a broadcast message
    private fun sendBroadcastMessage(message: String) {
        val broadcastIntent = Intent(channel).apply {
            putExtra("msg", message)
        }
        sendBroadcast(broadcastIntent)
    }
}
