package com.example.mobilesappdev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        toolBarSetup()
    }

    private fun toolBarSetup()
    {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id._toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.preferences)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}