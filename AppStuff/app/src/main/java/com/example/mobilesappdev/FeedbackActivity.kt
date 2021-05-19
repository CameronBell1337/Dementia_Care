package com.example.mobilesappdev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        toolBarSetup()
    }

    private fun toolBarSetup()
    {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id._toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.help_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}