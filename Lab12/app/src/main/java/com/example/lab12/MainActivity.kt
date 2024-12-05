package com.example.lab12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        applyWindowInsets()

        setupStartButton()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupStartButton() {
        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startServiceWithFeedback()
        }
    }

    private fun startServiceWithFeedback() {
        // Start the service
        startService(Intent(this, MyService::class.java))

        // Display a Toast message
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()

        // Close the Activity
        finish()
    }
}
