package com.example.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.extras?.getString("msg")?.let { message ->
                findViewById<TextView>(R.id.tvMsg).text = message
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        applyWindowInsets()

        setupButtonListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.btnMusic).setOnClickListener {
            registerChannel("music")
        }
        findViewById<Button>(R.id.btnNew).setOnClickListener {
            registerChannel("new")
        }
        findViewById<Button>(R.id.btnSport).setOnClickListener {
            registerChannel("sport")
        }
    }

    private fun registerChannel(channel: String) {
        val intentFilter = IntentFilter(channel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, intentFilter)
        }

        val serviceIntent = Intent(this, MyService::class.java).apply {
            putExtra("channel", channel)
        }
        startService(serviceIntent)
    }
}
